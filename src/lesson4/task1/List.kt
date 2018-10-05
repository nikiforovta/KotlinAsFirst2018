@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import lesson3.task1.digitCountInNumber
import lesson3.task1.digitNumber
import lesson3.task1.isPrime
import java.lang.Math.pow
import java.util.Collections.reverse
import kotlin.math.sqrt

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
        when {
            y < 0 -> listOf()
            y == 0.0 -> listOf(0.0)
            else -> {
                val root = sqrt(y)
                // Результат!
                listOf(-root, root)
            }
        }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun sum(a: List<Double>): Double {
    var k = 0.0
    for (i in 0 until a.size) k += a[i]
    return k
}

fun abs(v: List<Double>): Double =
        sqrt(sum(v.map { sqr(it) }))

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double =
        if (list.isEmpty()) 0.0 else (sum(list) / list.size)

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val k = mean(list)
    if (list.isNotEmpty()) for (i in 0 until list.size) list[i] = list[i] - k
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double {
    var c = 0.0
    if (a.isNotEmpty()) for (i in 0 until a.size) c += a[i] * b[i]
    return c
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double {
    var result = 0.0
    if (p.isNotEmpty()) for (i in 0 until p.size) result += p[i] * pow(x, i.toDouble())
    return result
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Double>): MutableList<Double> {
    if (list.isNotEmpty()) {
        var sp = list[0]
        for (i in 1 until list.size) {
            val c = list[i]
            list[i] = list[i] + sp
            sp += c
        }
    }
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var n1 = n
    var i = 2
    val res = mutableListOf<Int>()
    while (!isPrime(n1) && i <= sqrt(n1.toDouble()).toInt()) {
        while (n1 % i == 0) {
            res.add(i)
            n1 /= i
        }
        i++
    }
    if (n1 != 1) res.add(n1)
    return res
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String =
        factorize(n).joinToString(separator = "*")

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun reverseInt(a: List<Int>): List<Int> {
    val b: MutableList<Int> = a as MutableList<Int>
    if (a.size > 1)
        for (i in 0 until a.size / 2) {
            val k = b[b.size - i - 1]
            b[b.size - i - 1] = b[i]
            b[i] = k
        }
    return b
}

fun convert(n: Int, base: Int): List<Int> {
    var n1 = n
    val res = mutableListOf<Int>()
    while (n1 >= base) {
        res.add(n1 % base)
        n1 /= base
    }
    res.add(n1)
    return reverseInt(res)
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun intToString(i: Int): String = when (i) {
    10 -> "a"
    11 -> "b"
    12 -> "c"
    13 -> "d"
    14 -> "e"
    15 -> "f"
    16 -> "g"
    17 -> "h"
    18 -> "i"
    19 -> "j"
    20 -> "k"
    21 -> "l"
    22 -> "m"
    23 -> "n"
    24 -> "o"
    25 -> "p"
    26 -> "q"
    27 -> "r"
    28 -> "s"
    29 -> "t"
    30 -> "u"
    31 -> "v"
    32 -> "w"
    33 -> "x"
    34 -> "y"
    35 -> "z"
    else -> "$i"
}

fun convertToString(n: Int, base: Int): String {
    val pres = convert(n, base)
    val res = mutableListOf<String>()
    if (pres.size > 1) {
        for (i in 0 until pres.size) res.add(intToString(pres[i]))
    } else {
        res.add(intToString(pres[0]))
    }
    var kres = ""
    if (res.size > 1) {
        for (i in 0 until res.size) {
            kres += res[i]
        }
    } else {
        kres += res[0]
    }
    return kres
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var res = 0
    var i = 0
    if (digits.isNotEmpty()) do {
        res += digits[i] * pow(base.toDouble(), (digits.size - 1 - i).toDouble()).toInt()
        i++
    } while (i < digits.size)
    return res
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */
fun charToInt(ch: Char): Int {
    return when (ch) {
        '0' -> 0
        '1' -> 1
        '2' -> 2
        '3' -> 3
        '4' -> 4
        '5' -> 5
        '6' -> 6
        '7' -> 7
        '8' -> 8
        '9' -> 9
        'a' -> 10
        'b' -> 11
        'c' -> 12
        'd' -> 13
        'e' -> 14
        'f' -> 15
        'g' -> 16
        'h' -> 17
        'i' -> 18
        'j' -> 19
        'k' -> 20
        'l' -> 21
        'm' -> 22
        'n' -> 23
        'o' -> 24
        'p' -> 25
        'q' -> 26
        'r' -> 27
        's' -> 28
        't' -> 29
        'u' -> 30
        'v' -> 31
        'w' -> 32
        'x' -> 33
        'y' -> 34
        else -> 35
    }
}

fun decimalFromString(str: String, base: Int): Int {
    var res = 0
    var i = 0
    do {
        res += (charToInt(str[i]) * pow(base.toDouble(), (str.length - 1 - i).toDouble())).toInt()
        i++
    } while (i < str.length)
    return res
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */

fun roman(n: Int): String {
    var nr = ""
    var ka = digitNumber(n)
    var na = n
    while (na != 0) {
        if (ka == 6) when (na / pow(10.0, (ka - 1).toDouble()).toInt()) {
            1 -> nr += "MMMMMMMMMMMMMMMMMMM"
            2 -> nr += "MMMMMMMMMMMMMMMMMMMM"
            3 -> nr += "MMMMMMMMMMMMMMMMMMMMM"
            4 -> nr += "MMMMMMMMMMMMMMMMMMMMMM"
            5 -> nr += "MMMMMMMMMMMMMMMMMMMMMMM"
            6 -> nr += "MMMMMMMMMMMMMMMMMMMMMMMM"
            7 -> nr += "MMMMMMMMMMMMMMMMMMMMMMMMM"
            8 -> nr += "MMMMMMMMMMMMMMMMMMMMMMMMMM"
            9 -> nr += "MMMMMMMMMMMMMMMMMMMMMMMMMMM"
        }
        if (ka == 5) when (na / pow(10.0, (ka - 1).toDouble()).toInt()) {
            1 -> nr += "MMMMMMMMMM"
            2 -> nr += "MMMMMMMMMMM"
            3 -> nr += "MMMMMMMMMMMM"
            4 -> nr += "MMMMMMMMMMMMM"
            5 -> nr += "MMMMMMMMMMMMMM"
            6 -> nr += "MMMMMMMMMMMMMMM"
            7 -> nr += "MMMMMMMMMMMMMMMM"
            8 -> nr += "MMMMMMMMMMMMMMMMM"
            9 -> nr += "MMMMMMMMMMMMMMMMMM"
        }
        if (ka == 4) when (na / pow(10.0, (ka - 1).toDouble()).toInt()) {
            1 -> nr += "M"
            2 -> nr += "MM"
            3 -> nr += "MMM"
            4 -> nr += "MMMM"
            5 -> nr += "MMMMM"
            6 -> nr += "MMMMMM"
            7 -> nr += "MMMMMMM"
            8 -> nr += "MMMMMMMM"
            9 -> nr += "MMMMMMMMM"
        }
        if (ka == 3) when (na / pow(10.0, (ka - 1).toDouble()).toInt()) {
            1 -> nr += "C"
            2 -> nr += "CC"
            3 -> nr += "CCC"
            4 -> nr += "CD"
            5 -> nr += "D"
            6 -> nr += "DC"
            7 -> nr += "DCC"
            8 -> nr += "DCCC"
            9 -> nr += "CM"
        }
        if (ka == 2) when (na / pow(10.0, (ka - 1).toDouble()).toInt()) {
            1 -> nr += "X"
            2 -> nr += "XX"
            3 -> nr += "XXX"
            4 -> nr += "XL"
            5 -> nr += "L"
            6 -> nr += "LX"
            7 -> nr += "LXX"
            8 -> nr += "LXXX"
            9 -> nr += "XC"
        }
        if (ka == 1) when (na / pow(10.0, (ka - 1).toDouble()).toInt()) {
            1 -> nr += "I"
            2 -> nr += "II"
            3 -> nr += "III"
            4 -> nr += "IV"
            5 -> nr += "V"
            6 -> nr += "VI"
            7 -> nr += "VII"
            8 -> nr += "VIII"
            9 -> nr += "IX"
        }
        ka--
        na %= pow(10.0, ka.toDouble()).toInt()
    }
    return nr
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String {
    var rn = ""
    var k = digitNumber(n)
    var na = n
    var nc = na
    while (k != 0) {
        if (k == 6) {
            when (na / pow(10.0, (k - 1).toDouble()).toInt()) {
                1 -> rn += "сто"
                2 -> rn += "двести"
                3 -> rn += "триста"
                4 -> rn += "четыреста"
                5 -> rn += "пятьсот"
                6 -> rn += "шестьсот"
                7 -> rn += "семьсот"
                8 -> rn += "восемьсот"
                9 -> rn += "девятьсот"
            }
        }
        if (k == 5) {
            when (na / pow(10.0, (k - 2).toDouble()).toInt()) {
                11 -> rn += " одиннадцать тысяч"
                12 -> rn += " двенадцать тысяч"
                13 -> rn += " тринадцать тысяч"
                14 -> rn += " четырнадцать тысяч"
                15 -> rn += " пятнадцать тысяч"
                16 -> rn += " шестнадцать тысяч"
                17 -> rn += " семнадцать тысяч"
                18 -> rn += " восемнадцать тысяч"
                19 -> rn += " девятнадцать тысяч"
                else -> {
                    when (na / pow(10.0, (k - 1).toDouble()).toInt()) {
                        1 -> rn += " десять тысяч"
                        2 -> rn += " двадцать"
                        3 -> rn += " тридцать"
                        4 -> rn += " сорок"
                        5 -> rn += " пятьдесят"
                        6 -> rn += " шестьдесят"
                        7 -> rn += " семьдесят"
                        8 -> rn += " восемьдесят"
                        9 -> rn += " девяносто"
                    }
                }
            }
        }
        if (k == 4) {
            if (nc / pow(10.0, (k - 1).toDouble()).toInt() % 10 == 0) rn += " тысяч"
            if (nc / pow(10.0, (k - 1).toDouble()).toInt() !in 11..19 && nc / pow(10.0, k.toDouble()).toInt() != 1) when (na / pow(10.0, (k - 1).toDouble()).toInt()) {
                1 -> rn += " одна тысяча"
                2 -> rn += " две тысячи"
                3 -> rn += " три тысячи"
                4 -> rn += " четыре тысячи"
                5 -> rn += " пять тысяч"
                6 -> rn += " шесть тысяч"
                7 -> rn += " семь тысяч"
                8 -> rn += " восемь тысяч"
                9 -> rn += " девять тысяч"
            }
        }
        if (k == 3) {
            when (na / pow(10.0, (k - 1).toDouble()).toInt()) {
                1 -> rn += " сто"
                2 -> rn += " двести"
                3 -> rn += " триста"
                4 -> rn += " четыреста"
                5 -> rn += " пятьсот"
                6 -> rn += " шестьсот"
                7 -> rn += " семьсот"
                8 -> rn += " восемьсот"
                9 -> rn += " девятьсот"
            }
        }
        if (k == 2) {
            when (na / pow(10.0, (k - 2).toDouble()).toInt()) {
                11 -> rn += " одиннадцать"
                12 -> rn += " двенадцать"
                13 -> rn += " тринадцать"
                14 -> rn += " четырнадцать"
                15 -> rn += " пятнадцать"
                16 -> rn += " шестнадцать"
                17 -> rn += " семнадцать"
                18 -> rn += " восемнадцать"
                19 -> rn += " девятнадцать"
                else -> {
                    when (na / pow(10.0, (k - 1).toDouble()).toInt()) {
                        1 -> rn += " десять"
                        2 -> rn += " двадцать"
                        3 -> rn += " тридцать"
                        4 -> rn += " сорок"
                        5 -> rn += " пятьдесят"
                        6 -> rn += " шестьдесят"
                        7 -> rn += " семьдесят"
                        8 -> rn += " восемьдесят"
                        9 -> rn += " девяносто"
                    }
                }
            }
        }
        if ((k == 1) && (nc / pow(10.0, (k - 1).toDouble()).toInt() !in 11..19)) {
            when (na / pow(10.0, (k - 1).toDouble()).toInt()) {
                1 -> rn += " один"
                2 -> rn += " два"
                3 -> rn += " три"
                4 -> rn += " четыре"
                5 -> rn += " пять"
                6 -> rn += " шесть"
                7 -> rn += " семь"
                8 -> rn += " восемь"
                9 -> rn += " девять"
            }
        }
        nc = na
        k--
        na %= pow(10.0, k.toDouble()).toInt()
    }
    return rn.replace("  ", " ").trim()
}