@file:Suppress("UNUSED_PARAMETER")

package lesson2.task1

import lesson1.task1.discriminant
import kotlin.math.max
import kotlin.math.sqrt

/**
 * Пример
 *
 * Найти число корней квадратного уравнения ax^2 + bx + c = 0
 */
fun quadraticRootNumber(a: Double, b: Double, c: Double): Int {
    val discriminant = discriminant(a, b, c)
    return when {
        discriminant > 0.0 -> 2
        discriminant == 0.0 -> 1
        else -> 0
    }
}

/**
 * Пример
 *
 * Получить строковую нотацию для оценки по пятибалльной системе
 */
fun gradeNotation(grade: Int): String = when (grade) {
    5 -> "отлично"
    4 -> "хорошо"
    3 -> "удовлетворительно"
    2 -> "неудовлетворительно"
    else -> "несуществующая оценка $grade"
}

/**
 * Пример
 *
 * Найти наименьший корень биквадратного уравнения ax^4 + bx^2 + c = 0
 */
fun minBiRoot(a: Double, b: Double, c: Double): Double {
    // 1: в главной ветке if выполняется НЕСКОЛЬКО операторов
    if (a == 0.0) {
        if (b == 0.0) return Double.NaN // ... и ничего больше не делать
        val bc = -c / b
        if (bc < 0.0) return Double.NaN // ... и ничего больше не делать
        return -sqrt(bc)
        // Дальше функция при a == 0.0 не идёт
    }
    val d = discriminant(a, b, c)   // 2
    if (d < 0.0) return Double.NaN  // 3
    // 4
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    val y3 = max(y1, y2)       // 5
    if (y3 < 0.0) return Double.NaN // 6
    return -sqrt(y3)           // 7
}

/**
 * Простая
 *
 * Мой возраст. Для заданного 0 < n < 200, рассматриваемого как возраст человека,
 * вернуть строку вида: «21 год», «32 года», «12 лет».
 */
fun ageDescription(age: Int): String {
    var res = ""
    val che1 = age % 10
    val che2 = age % 100
    when {
        che2 == 0 -> res = "$age лет"
        che2 == 1 -> res = "$age год"
        che2 in (2..4) -> res = "$age года"
        che2 in (5..20) -> res = "$age лет"
        che2 == 21 -> res = "$age год"
        che1 in (2..4) && che2 !in (2..4) -> res = "$age года"
        che1 in (5..9) || che1 == 0 && che2 !in (5..20) -> res = "$age лет"
        che1 % 10 == 1 && che2 != 1 -> res = "$age год"

    }
    return res
}

/**
 * Простая
 *
 * Путник двигался t1 часов со скоростью v1 км/час, затем t2 часов — со скоростью v2 км/час
 * и t3 часов — со скоростью v3 км/час.
 * Определить, за какое время он одолел первую половину пути?
 */
fun timeForHalfWay(t1: Double, v1: Double,
                   t2: Double, v2: Double,
                   t3: Double, v3: Double): Double {
    var t: Double
    val s = (v1 * t1 + v2 * t2 + v3 * t3) / 2
    val s0: Double
    if (s >= v1 * t1) {
        if (s >= v1 * t1 + v2 * t2) {
            t = t1 + t2
            s0 = v1 * t1 + v2 * t2
            t += (s - s0) / v3
        } else {
            t = t1
            s0 = v1 * t1
            t += (s - s0) / v2
        }
    } else {
        t = s / v1
    }
    return t
}

/**
 * Простая
 *
 * Нa шахматной доске стоят черный король и две белые ладьи (ладья бьет по горизонтали и вертикали).
 * Определить, не находится ли король под боем, а если есть угроза, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от первой ладьи, 2, если только от второй ладьи,
 * и 3, если угроза от обеих ладей.
 * Считать, что ладьи не могут загораживать друг друга
 */
fun whichRookThreatens(kingX: Int, kingY: Int,
                       rookX1: Int, rookY1: Int,
                       rookX2: Int, rookY2: Int): Int {
    var dang = 0
    if (kingX == rookX1 || kingY == rookY1) dang = 1
    if (kingX == rookX2 || kingY == rookY2) dang = 2
    if ((kingX == rookX1 || kingY == rookY1) && (kingX == rookX2 || kingY == rookY2)) dang = 3
    return dang
}

/**
 * Простая
 *
 * На шахматной доске стоят черный король и белые ладья и слон
 * (ладья бьет по горизонтали и вертикали, слон — по диагоналям).
 * Проверить, есть ли угроза королю и если есть, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от ладьи, 2, если только от слона,
 * и 3, если угроза есть и от ладьи и от слона.
 * Считать, что ладья и слон не могут загораживать друг друга.
 */
fun rookOrBishopThreatens(kingX: Int, kingY: Int,
                          rookX: Int, rookY: Int,
                          bishopX: Int, bishopY: Int): Int {
    var danger = 0
    val b1 = kingY - kingX
    val b2 = kingY + kingX
    if (kingX == rookX || kingY == rookY) danger = 1
    if (bishopY == bishopX + b1 || bishopY == -bishopX + b2) danger = 2
    if ((kingX == rookX || kingY == rookY) && (bishopY == bishopX + b1 || bishopY == -bishopX + b2)) danger = 3
    return danger
}

/**
 * Простая
 *
 * Треугольник задан длинами своих сторон a, b, c.
 * Проверить, является ли данный треугольник остроугольным (вернуть 0),
 * прямоугольным (вернуть 1) или тупоугольным (вернуть 2).
 * Если такой треугольник не существует, вернуть -1.
 */
fun triangleKind(a: Double, b: Double, c: Double): Int {
    var ang = -1
    if (a >= b && a >= c && b + c > a) {
        when {
            ((a * a - b * b - c * c) / (-2 * b * c) < 0) -> ang = 2
            ((a * a - b * b - c * c) / (-2 * b * c) > 0) -> ang = 0
            (b * b + c * c == a * a) -> ang = 1
        }
    }
    if (b >= a && b >= c && a + c > b) {
        when {
            ((b * b - a * a - c * c) / (-2 * a * c) < 0) -> ang = 2
            ((b * b - a * a - c * c) / (-2 * a * c) > 0) -> ang = 0
            (a * a + c * c == b * b) -> ang = 1
        }
    }
    if (c >= a && c >= b && a + b > c) {
        when {
            ((c * c - b * b - a * a) / (-2 * b * a) < 0) -> ang = 2
            ((c * c - b * b - a * a) / (-2 * b * a) > 0) -> ang = 0
            (b * b + a * a == c * c) -> ang = 1
        }
    }
    return ang
}

/**
 * Средняя
 *
 * Даны четыре точки на одной прямой: A, B, C и D.
 * Координаты точек a, b, c, d соответственно, b >= a, d >= c.
 * Найти длину пересечения отрезков AB и CD.
 * Если пересечения нет, вернуть -1.
 */
fun segmentLength(a: Int, b: Int, c: Int, d: Int): Int {
    var cross = 0
    when {
        ((c > b) || (d < a)) -> cross = -1
        (c >= a && d >= b) -> cross = b - c
        (c >= a && d <= b) -> cross = d - c
        (c <= a && d >= b) -> cross = b - a
        (c <= a && d >= a && b >= d) -> cross = d - a
    }
    return cross
}