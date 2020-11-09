package com.github.terrakok.cicerone.graph

import com.github.terrakok.cicerone.*


class GraphRouter(
    private val root: Root
) : BaseRouter() {
    private val vertexes: Map<String, Vertex>
    private val currentPath: MutableList<String>
    val currentVertex get() = vertexes.getValue(currentPath.last())

    init {
        val allVertexes = mutableMapOf<String, Vertex>()
        val links = mutableSetOf<VertexLink>()
        val jumps = mutableListOf<List<String>>()
        validateGraph(root.vertex, allVertexes, links, jumps)

        val linkAndJumpIds = links.map { it.id } + jumps.flatten()
        linkAndJumpIds.forEach { id ->
            require(allVertexes.containsKey(id)) { "Not found vertex for id=$id" }
        }

        vertexes = allVertexes
        currentPath = mutableListOf(root.vertex.id)

        jumps.forEach {
            require(validateJump(it)) { "Invalid jump path=$it" }
        }

        if (root.defaultDestination != null) {
            require(root.vertex.edges.any { it.id == root.defaultDestination })  { "Not found default destination" }
            navigateTo(root.defaultDestination)
        }
    }

    private fun validateGraph(
        vertex: Vertex,
        allVertexes: MutableMap<String, Vertex>,
        links: MutableSet<VertexLink>,
        jumps: MutableList<List<String>>
    ) {
        if (allVertexes.containsKey(vertex.id)) error("Graph contains duplicate id ${vertex.id}")
        allVertexes[vertex.id] = vertex

        links.addAll(vertex.edges.filterIsInstance<VertexLink>())
        jumps.addAll(vertex.jumps.map { listOf(it.backTo ?: vertex.id, *it.chain.toTypedArray()) })
        vertex.edges.forEach {
            if (it !is VertexLink) validateGraph(it, allVertexes, links, jumps)
        }
    }

    private fun validateJump(path: List<String>): Boolean {
        if (path.isEmpty()) return false
        if (path.size == 1) return true

        val v = vertexes.getValue(path[0])
        v.edges.firstOrNull { it.id == path[1] } ?: return false
        return validateJump(path.subList(1, path.size))
    }

    fun navigateTo(
        vertexId: String,
        screenFactory: (vertexId: String) -> Screen? = { null }
    ) {
        val destination = currentVertex.edges.first { it.id == vertexId }
        val screen = createScreen(destination.id, screenFactory)
        val command =
            if (currentVertex.id == Root.ID) Replace(screen)
            else Forward(screen, destination.destroyPreviousView)
        currentPath.add(destination.id)
        executeCommands(command)
    }

    fun jumpTo(
        jumpId: String,
        screenFactory: (vertexId: String) -> Screen? = { null }
    ) {
        val jump = currentVertex.jumps.first { it.id == jumpId }

        if (jump.backTo == Root.ID && jump.chain.isEmpty()) {
            finish()
            return
        }

        val commands = mutableListOf<Command>().apply {
            if (jump.backTo != null) {
                val id = jump.backTo
                val index = currentPath.indexOfFirst { it == id }
                if (index == -1) error("Current path doesn't contain vertex $id")
                currentPath.subList(index + 1, currentPath.size).clear()
                if (jump.backTo == Root.ID) add(BackTo(null))
                else add(BackTo(Key(id)))
            }
            jump.chain.forEachIndexed { index, vertexId ->
                val screen = createScreen(vertexId, screenFactory)
                currentPath.add(vertexId)
                if (index == 0 && jump.backTo == Root.ID) {
                    add(Replace(screen))
                } else {
                    add(Forward(screen, vertexes.getValue(vertexId).destroyPreviousView))
                }
            }
        }

        executeCommands(*commands.toTypedArray())
    }

    fun exit() {
        currentPath.removeLast()
        executeCommands(Back())
    }

    private fun finish() {
        currentPath.clear()
        currentPath.add(Root.ID)
        executeCommands(BackTo(null), Back())
    }

    private fun createScreen(
        vertexId: String,
        screenFactory: (vertexId: String) -> Screen?
    ): Screen {
        val screen = screenFactory(vertexId)
            ?: vertexes.getValue(vertexId).screenFactory(vertexId)
            ?: error("Unknown screen for vertex $vertexId")
        require(screen.screenKey == vertexId) { "Screen key must be equal vertex id!" }
        return screen
    }

    private class Key(
        override val screenKey: String
    ) : Screen
}