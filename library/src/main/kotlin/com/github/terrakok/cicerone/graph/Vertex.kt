package com.github.terrakok.cicerone.graph

import com.github.terrakok.cicerone.*

open class Vertex internal constructor(
    val id: String,
    val edges: Set<Vertex>,
    val jumps: Set<Jump>,
    val destroyPreviousView: Boolean = true,
    val screenFactory: (id: String) -> Screen? = { null }
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Vertex

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

internal class VertexLink(
    id: String,
    destroyPreviousView: Boolean = true
): Vertex(id, emptySet(), emptySet(), destroyPreviousView)

class Jump(
    val id: String,
    val backTo: String?,
    val chain: List<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Jump

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

//Graph DSL

class GraphInfo(
    var edges: MutableSet<Vertex>.() -> Unit = {},
    var jumps: MutableSet<Jump>.() -> Unit = {}
)

class Root(
    val vertex: Vertex,
    val defaultDestination: String?
) {
    companion object {
        const val ID = "graph-root"
    }
}

fun graph(
    defaultDestination: String? = null,
    setup: GraphInfo.() -> Unit
): Root {
    val info = GraphInfo().apply(setup)
    val v = Vertex(
        Root.ID,
        mutableSetOf<Vertex>().apply(info.edges),
        mutableSetOf<Jump>().apply(info.jumps)
    )
    return Root(v, defaultDestination)
}

class VertexInfo(
    var edges: MutableSet<Vertex>.() -> Unit = {},
    var jumps: MutableSet<Jump>.() -> Unit = {},
    var screen: (id: String) -> Screen? = { null }
)

fun MutableSet<Vertex>.dest(
    id: String,
    destroyPreviousView: Boolean = true,
    setup: VertexInfo.() -> Unit = {}
) {
    val info = VertexInfo().apply(setup)
    add(Vertex(
        id,
        mutableSetOf<Vertex>().apply(info.edges),
        mutableSetOf<Jump>().apply(info.jumps),
        destroyPreviousView,
        info.screen
    ))
}

fun MutableSet<Vertex>.edge(
    id: String,
    destroyPreviousView: Boolean = true
) {
    add(VertexLink(id, destroyPreviousView))
}

class JumpInfo(
    var backTo: String? = null,
    internal var chain: List<String> = emptyList()
) {
    fun chain(vararg id: String) {
        chain = id.toList()
    }
}

fun MutableSet<Jump>.jump(
    id: String,
    setup: JumpInfo.() -> Unit
) {
    val info = JumpInfo().apply(setup)
    add(Jump(
        id,
        info.backTo,
        info.chain
    ))
}

fun MutableSet<Jump>.finish(id: String) {
    add(Jump(id, Root.ID, emptyList()))
}