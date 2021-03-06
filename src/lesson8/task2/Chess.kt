@file:Suppress("UNUSED_PARAMETER")
package lesson8.task2

import lesson8.task3.Graph
import kotlin.math.abs

/**
 * Клетка шахматной доски. Шахматная доска квадратная и имеет 8 х 8 клеток.
 * Поэтому, обе координаты клетки (горизонталь row, вертикаль column) могут находиться в пределах от 1 до 8.
 * Горизонтали нумеруются снизу вверх, вертикали слева направо.
 */
data class Square(val column: Int, val row: Int) {
    /**
     * Пример
     *
     * Возвращает true, если клетка находится в пределах доски
     */
    fun inside(): Boolean = column in 1..8 && row in 1..8

    /**
     * Простая
     *
     * Возвращает строковую нотацию для клетки.
     * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
     * Для клетки не в пределах доски вернуть пустую строку
     */
    fun notation(): String = if (inside()) 'a' + column - 1 + "$row" else ""
}

/**
 * Простая
 *
 * Создаёт клетку по строковой нотации.
 * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
 * Если нотация некорректна, бросить IllegalArgumentException
 */
fun square(notation: String): Square {
    if (notation.length != 2) throw IllegalArgumentException()
    if (notation[0] in 'a'..'h' && notation[1] in '1'..'8')
        return Square(notation[0] - 'a' + 1, notation[1] - '0')
    else throw IllegalArgumentException()
}

/**
 * Простая
 *
 * Определить число ходов, за которое шахматная ладья пройдёт из клетки start в клетку end.
 * Шахматная ладья может за один ход переместиться на любую другую клетку
 * по вертикали или горизонтали.
 * Ниже точками выделены возможные ходы ладьи, а крестиками -- невозможные:
 *
 * xx.xxххх
 * xх.хxххх
 * ..Л.....
 * xх.хxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: rookMoveNumber(Square(3, 1), Square(6, 3)) = 2
 * Ладья может пройти через клетку (3, 3) или через клетку (6, 1) к клетке (6, 3).
 */
fun rookMoveNumber(start: Square, end: Square): Int {
    var res = 0
    if (!start.inside() || !end.inside()) throw IllegalArgumentException()
    if (start.row != end.row) res++
    if (start.column != end.column) res++
    return res
}

/**
 * Средняя
 *
 * Вернуть список из клеток, по которым шахматная ладья может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов ладьи см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: rookTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможен ещё один вариант)
 *          rookTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(3, 3), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          rookTrajectory(Square(3, 5), Square(8, 5)) = listOf(Square(3, 5), Square(8, 5))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun rookTrajectory(start: Square, end: Square): List<Square> = setOf(start, Square(start.column, end.row), end).toList()

/**
 * Простая
 *
 * Определить число ходов, за которое шахматный слон пройдёт из клетки start в клетку end.
 * Шахматный слон может за один ход переместиться на любую другую клетку по диагонали.
 * Ниже точками выделены возможные ходы слона, а крестиками -- невозможные:
 *
 * .xxx.ххх
 * x.x.xххх
 * xxСxxxxx
 * x.x.xххх
 * .xxx.ххх
 * xxxxx.хх
 * xxxxxх.х
 * xxxxxхх.
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если клетка end недостижима для слона, вернуть -1.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Примеры: bishopMoveNumber(Square(3, 1), Square(6, 3)) = -1; bishopMoveNumber(Square(3, 1), Square(3, 7)) = 2.
 * Слон может пройти через клетку (6, 4) к клетке (3, 7).
 */
fun bishopMoveNumber(start: Square, end: Square): Int {
    if (!start.inside() || !end.inside()) throw IllegalArgumentException()
    if ((start.column + start.row) % 2 != (end.column + end.row) % 2) return -1
    var res = 0
    if (start.row + start.column != end.row + end.column) res++
    if (start.column - start.row != end.column - end.row) res++
    return res
}

/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный слон может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов слона см. предыдущую задачу.
 *
 * Если клетка end недостижима для слона, вернуть пустой список.
 *
 * Если клетка достижима:
 * - список всегда включает в себя клетку start
 * - клетка end включается, если она не совпадает со start.
 * - между ними должны находиться промежуточные клетки, по порядку от start до end.
 *
 * Примеры: bishopTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          bishopTrajectory(Square(3, 1), Square(3, 7)) = listOf(Square(3, 1), Square(6, 4), Square(3, 7))
 *          bishopTrajectory(Square(1, 3), Square(6, 8)) = listOf(Square(1, 3), Square(6, 8))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun bishopTrajectory(start: Square, end: Square): List<Square> {
    if (!start.inside() || !end.inside()) throw IllegalArgumentException()
    if ((start.column + start.row) % 2 != (end.column + end.row) % 2) return emptyList()
    val res = mutableListOf<Square>()
    res.add(start)
    when (bishopMoveNumber(start, end)) {
        1 -> res.add(end)
        2 -> {
            val firstRow = (start.row + start.column - end.row + end.column) / 2 + end.row - end.column
            val secondRow = (end.row + end.column - start.row + start.column) / 2 + start.row - start.column
            var firstCheck = setOf(start.column + abs(firstRow - start.row), start.column - abs(firstRow - start.row))
            var secondCheck = setOf(-(abs(end.row - firstRow) - end.column), -(-abs(end.row - firstRow) - end.column))
            if (firstCheck.intersect(secondCheck).toIntArray().none { it in 1..8 }) {
                firstCheck = setOf(start.column + abs(secondRow - start.row), start.column - abs(secondRow - start.row))
                secondCheck = setOf(-(abs(end.row - secondRow) - end.column), -(-abs(end.row - secondRow) - end.column))
                if (firstCheck.intersect(secondCheck).toIntArray().none { it in 1..8 }) return emptyList()
                else res.addAll(listOf(Square(firstCheck.intersect(secondCheck).toIntArray()
                        .filter { it in 1..8 }[0], secondRow), end))
            } else res.addAll(listOf(Square(firstCheck.intersect(secondCheck).toIntArray()
                    .filter { it in 1..8 }[0], firstRow), end))
        }
    }
    return res
}

/**
 * Средняя
 *
 * Определить число ходов, за которое шахматный король пройдёт из клетки start в клетку end.
 * Шахматный король одним ходом может переместиться из клетки, в которой стоит,
 * на любую соседнюю по вертикали, горизонтали или диагонали.
 * Ниже точками выделены возможные ходы короля, а крестиками -- невозможные:
 *
 * xxxxx
 * x...x
 * x.K.x
 * x...x
 * xxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: kingMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Король может последовательно пройти через клетки (4, 2) и (5, 2) к клетке (6, 3).
 */
fun kingMoveNumber(start: Square, end: Square): Int {
    if (!start.inside() || !end.inside()) throw IllegalArgumentException()
    return maxOf(abs(start.row - end.row), abs(start.column - end.column))
}

/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный король может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов короля см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: kingTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможны другие варианты)
 *          kingTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(4, 2), Square(5, 2), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          kingTrajectory(Square(3, 5), Square(6, 2)) = listOf(Square(3, 5), Square(4, 4), Square(5, 3), Square(6, 2))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun kingTrajectory(start: Square, end: Square): List<Square> {
    val res = mutableListOf<Square>()
    res.add(start)
    var i = 0
    while (true) {
        if (res.last().row == end.row || res.last().column == end.column) break
        if (end.row > start.row && end.column > start.column) res.add(Square(res[i].column + 1, res[i].row + 1))
        if (end.row < start.row && end.column > start.column) res.add(Square(res[i].column + 1, res[i].row - 1))
        if (end.row < start.row && end.column < start.column) res.add(Square(res[i].column - 1, res[i].row - 1))
        if (end.row > start.row && end.column < start.column) res.add(Square(res[i].column - 1, res[i].row + 1))
        i++
    }
    if (res.last().row == end.row) {
        repeat(maxOf(abs(start.row - end.row), abs(start.column - end.column)) -
                minOf(abs(start.row - end.row), abs(start.column - end.column))) {
            if (end.column > start.column) res.add(Square(res.last().column + 1, res.last().row))
            else res.add(Square(res.last().column - 1, res.last().row))
        }
    } else {
        repeat(maxOf(abs(start.row - end.row), abs(start.column - end.column)) -
                minOf(abs(start.row - end.row), abs(start.column - end.column))) {
            if (end.row > start.row) res.add(Square(res.last().column, res.last().row + 1))
            else res.add(Square(res.last().column, res.last().row - 1))
        }
    }
    return res
}

/**
 * Сложная
 *
 * Определить число ходов, за которое шахматный конь пройдёт из клетки start в клетку end.
 * Шахматный конь одним ходом вначале передвигается ровно на 2 клетки по горизонтали или вертикали,
 * а затем ещё на 1 клетку под прямым углом, образуя букву "Г".
 * Ниже точками выделены возможные ходы коня, а крестиками -- невозможные:
 *
 * .xxx.xxx
 * xxKxxxxx
 * .xxx.xxx
 * x.x.xxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: knightMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Конь может последовательно пройти через клетки (5, 2) и (4, 4) к клетке (6, 3).
 */

fun createKnightGraph(): Graph {
    val g = Graph()
    val knightMoves = listOf(Pair(-1, -2), Pair(-2, -1), Pair(-2, 1), Pair(1, -2),
            Pair(-1, 2), Pair(2, -1), Pair(1, 2), Pair(2, 1))
    for (i in 'a'..'h') {
        for (j in 1..8) {
            g.addVertex("$i$j")
        }
    }
    for (i in 1..8) {
        for (j in 1..8) {
            val x = Square(i, j).notation()
            for ((first, second) in knightMoves) {
                var y: String
                if (i + first in 1..8 && j + second in 1..8)
                    y = Square(i + first, j + second).notation()
                else continue
                g.connect(x, y)
            }
        }
    }
    return g
}

fun knightMoveNumber(start: Square, end: Square): Int = createKnightGraph().bfs(start.notation(), end.notation())

/**
 * Очень сложная
 *
 * Вернуть список из клеток, по которым шахматный конь может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов коня см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры:
 *
 * knightTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 * здесь возможны другие варианты)
 * knightTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(5, 2), Square(4, 4), Square(6, 3))
 * (здесь возможен единственный вариант)
 * knightTrajectory(Square(3, 5), Square(5, 6)) = listOf(Square(3, 5), Square(5, 6))
 * (здесь опять возможны другие варианты)
 * knightTrajectory(Square(7, 7), Square(8, 8)) =
 *     listOf(Square(7, 7), Square(5, 8), Square(4, 6), Square(6, 7), Square(8, 8))
 *
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun knightTrajectory(start: Square, end: Square): List<Square> = TODO()
