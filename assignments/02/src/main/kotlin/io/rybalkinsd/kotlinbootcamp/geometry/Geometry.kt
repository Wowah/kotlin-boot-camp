package io.rybalkinsd.kotlinbootcamp.geometry

// import javax.print.attribute.standard.MediaSize

/**
 * Entity that can physically intersect, like flame and player
 */
interface Collider {
    fun isColliding(other: Collider): Boolean
}

/**
 * 2D point with integer coordinates
 */
data class Point(val x: Int, val y: Int) : Collider {
    override fun isColliding(other: Collider): Boolean {
        return when (other) {
            is Point -> (x == other.x) and (y == other.y)
            is Bar -> {
                val arrayX = listOf(other.firstCornerX, other.secondCornerX).sorted()
                val arrayY = listOf(other.firstCornerY, other.secondCornerY).sorted()
                (arrayX[0] <= x) and (x <= arrayX[1]) and (arrayY[0] <= y) and (y <= arrayY[1])
            }
            else -> false
        }
    }
}

/**
 * Bar is a rectangle, which borders are parallel to coordinate axis
 * Like selection bar in desktop, this bar is defined by two opposite corners
 * Bar is not oriented
 * (It does not matter, which opposite corners you choose to define bar)
 */
data class Bar(val firstCornerX: Int, val firstCornerY: Int, val secondCornerX: Int, val secondCornerY: Int) : Collider {
    override fun isColliding(other: Collider): Boolean {
        val arrayX = listOf(firstCornerX, secondCornerX).sorted()
        val arrayY = listOf(firstCornerY, secondCornerY).sorted()
        return when (other) {
            is Point -> (arrayX[0] <= other.x) and (other.x <= arrayX[1]) and
                        (arrayY[0] <= other.y) and (other.y <= arrayY[1])
            is Bar -> {
                val secondArrayX = listOf(other.firstCornerX, other.secondCornerX).sorted()
                val secondArrayY = listOf(other.firstCornerY, other.secondCornerY).sorted()
                val intersectByX = if (arrayX[0] < secondArrayX[0]) (arrayX[1] >= secondArrayX[0]) else (arrayX[0] <= secondArrayX[1])
                val intersectByY = if (arrayY[0] < secondArrayY[0]) (arrayY[1] >= secondArrayY[0]) else (arrayY[0] <= secondArrayY[1])
                intersectByX and intersectByY
            }
            else -> false
        }
    }

    override fun equals(b: Any?) =
        when (b) {
            is Bar -> listOf(firstCornerX, secondCornerX).containsAll(listOf(b.firstCornerX, b.secondCornerX)) and
                    listOf(firstCornerY, secondCornerY).containsAll(listOf(b.firstCornerY, b.secondCornerY))
            else -> false
        }
}