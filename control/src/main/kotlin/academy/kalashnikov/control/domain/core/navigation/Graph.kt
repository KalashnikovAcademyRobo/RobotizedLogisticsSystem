package academy.kalashnikov.control.domain.core.navigation

object Graph {
    val root: Node

    init {
        val node10Neighbors = mutableListOf<NodeDirection>()
        val node10 = Node(neighbors = node10Neighbors, isNoStopPoint = true)
        val node9 = Node(neighbors = listOf(NodeDirection(node10, Direction.LEFT)), isNoStopPoint = true)
        val node8 = Node(neighbors = listOf(NodeDirection(node9, Direction.FORWARD)), isNoStopPoint = true)
        val node7 = Node(neighbors = listOf(NodeDirection(node8, Direction.RIGHT)), isNoStopPoint = false)
        val node6 = Node(neighbors = listOf(NodeDirection(node7, Direction.FORWARD)), isNoStopPoint = true)
        val node5 = Node(neighbors = listOf(NodeDirection(node6, Direction.LEFT)), isNoStopPoint = true)
        val node4 = Node(neighbors = listOf(NodeDirection(node5, Direction.BACK)), isNoStopPoint = true)
        val node3 = Node(neighbors = listOf(NodeDirection(node4, Direction.RIGHT)), isNoStopPoint = true)
        val node2 = Node(neighbors = listOf(NodeDirection(node3, Direction.FORWARD)), isNoStopPoint = true)
        val node1 = Node(neighbors = listOf(NodeDirection(node2, Direction.LEFT)), isNoStopPoint = false)
        node10Neighbors.add(NodeDirection(node1, Direction.BACK))
        root = node1
    }

}
