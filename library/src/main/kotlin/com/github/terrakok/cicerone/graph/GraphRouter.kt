package com.github.terrakok.cicerone.graph

import com.github.terrakok.cicerone.*


class GraphRouter(
    private val root: Vertex
) : BaseRouter() {
    private val vertexes: Map<String, Vertex>
    private val currentPath = mutableListOf(root.id)
    val currentVertex get() = vertexes.getValue(currentPath.last())

    init {
        val map = mutableMapOf<String, Vertex>()
        validateGraph(root, map)
        vertexes = map
    }

    private fun validateGraph(vertex: Vertex, map: MutableMap<String, Vertex>) {
        if (map.containsKey(vertex.id)) error("Graph contains duplicate id ${vertex.id}")
        map[vertex.id] = vertex
        vertex.edges.forEach { validateGraph(it, map) }
    }

    fun navigateTo(
        vertexId: String,
        screenFactory: (vertexId: String) -> Screen? = { null }
    ) {
        //check navigation availability
        if (
            currentVertex.edges.none { it.id == vertexId }
            && !currentVertex.jumps.contains(vertexId)
        ) {
            error("Destination not available from vertex ${currentVertex.id}")
        }

        //may be just new screen?
        val edge = currentVertex.edges.firstOrNull { it.id == vertexId }
        if (edge != null) {
            val screen = createScreen(edge.id, screenFactory)
            val command =
                if (currentVertex.id == root.id) Replace(screen)
                else Forward(screen, edge.destroyPreviousView)
            currentPath.add(edge.id)
            executeCommands(command)
            return
        }

        //calculate jump
        val newPath = findJumpPath(vertexId)

        //is it finish jump?
        if (newPath.backToVertexId == root.id && newPath.chain.isEmpty()) {
            finish()
            return
        }

        //build commands for jump
        val commands = mutableListOf<Command>().apply {
            if (newPath.backToVertexId != null) {
                val id = newPath.backToVertexId
                val index = currentPath.indexOfLast { it == id }
                if (index == -1) error("Current path doesn't contain vertex $id")
                val forRemove = currentPath.subList(index + 1, currentPath.size - 1)
                currentPath.removeAll(forRemove)
                add(BackTo(Key(id)))
            }
            newPath.chain.forEachIndexed { index, vertexId ->
                val screen = createScreen(vertexId, screenFactory)
                currentPath.add(vertexId)
                if (index == 0 && newPath.backToVertexId == root.id) {
                    add(Replace(screen))
                } else {
                    add(Forward(screen, vertexes.getValue(vertexId).destroyPreviousView))
                }
            }
        }

        executeCommands(*commands.toTypedArray())
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

    fun exit() {
        currentPath.removeLast()
        executeCommands(Back())
    }

    fun finish() {
        currentPath.clear()
        currentPath.add(root.id)
        executeCommands(BackTo(Key(root.id)), Back())
    }

    private fun findJumpPath(vertexId: String): Path {


        TODO()
    }

    private class Key(
        override val screenKey: String
    ) : Screen

    private class Path(
        val backToVertexId: String?,
        val chain: List<String>
    )
}