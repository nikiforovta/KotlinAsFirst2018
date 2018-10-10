@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import javafx.beans.binding.Bindings.isNotEmpty
import jdk.nashorn.internal.objects.NativeArray.indexOf
import lesson1.task1.discriminant
import lesson1.task1.sqr
import lesson3.task1.digitCountInNumber
import lesson3.task1.digitNumber
import lesson3.task1.isPrime
import java.lang.Math.pow
import java.util.Collections.reverse
import java.util.Collections.swap
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

fun abs(v: List<Double>): Double =
        sqrt((v.map { sqr(it) }).sum())

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double =
        if (list.isEmpty()) 0.0 else (list.sum() / list.size)

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
    list.replaceAll { it - k }
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double =
        a.mapIndexed { i, _ -> a[i] * b[i] }.sum()

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double =
        (p.mapIndexed { i, _ -> p[i] * pow(x, i.toDouble()) }).sum()

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
            sp += list[i]
            list[i] = sp
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

fun convert(n: Int, base: Int): List<Int> {
    var n1 = n
    val res = mutableListOf<Int>()
    while (n1 >= base) {
        res.add(0, n1 % base)
        n1 /= base
    }
    res.add(0, n1)
    return res
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun convertToString(n: Int, base: Int): String =
        convert(n, base).joinToString(separator = "") { it.toString(36) }

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int =
        digits.foldRightIndexed(0)
        { i, element, acc -> acc + element * (pow(base.toDouble(), (digits.size - 1 - i).toDouble())).toInt() }

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */

fun charToInt(i: Char): Int = when (i) {
    in '0'..'9' -> (i.toInt() - '0'.toInt())
    else -> ((i).toInt() - 'W'.toInt())
}

fun decimalFromString(str: String, base: Int): Int =
        decimal((str.map { charToInt(it) }), base)

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */

fun roman(n: Int): String {
    val rom = listOf("I", "X", "C", "V", "L", "D", "X", "C", "M")
    var nr = ""
    var ka = digitNumber(n)
    var na = n
    while (na != 0) {
        val m = na / pow(10.0, (ka - 1).toDouble()).toInt()
        nr += if (ka >= 4) {
            rom[8].repeat(ka % 4 + m)
        } else {
            when (m) {
                0 -> ""
                in 1..3 -> rom[ka - 1].repeat(m)
                4 -> rom[ka - 1] + rom[ka + 2]
                in 5..8 -> rom[ka + 2] + rom[ka - 1].repeat(m - 5)
                else -> rom[ka - 1] + rom[ka + 5]
            }
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
    val diglast = arrayOf("ноль", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять")
    val digfirst = arrayOf("ноль", "одна", "две", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять")
    val hund = arrayOf("", "сто", "двести", "триста", "четыреста", "пятьсот",
            "шестьсот", "семьсот", "восемьсот", "девятьсот")
    val tens = arrayOf("", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать",
            "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать", "двадцать")
    val tens2 = arrayOf("", "десять", "двадцать", "тридцать", "сорок", "пятьдесят",
            "шестьдесят", "семьдесят", "восемьдесят", "девяносто")
    val forms = arrayOf("тысяча", "тысячи", "тысяч", "1")
    var rn = ""
    val nfirst = n / 1000
    val nlast = n % 1000
    if (nfirst != 0) {
        if (nfirst / 100 != 0) rn += hund[nfirst / 100] + " "
        rn += if (nfirst % 100 in 11..19) tens[nfirst % 10] + " "
        else tens2[nfirst / 10 % 10] + " "
        if (nfirst % 10 != 0 && nfirst % 100 !in 11..19) rn += digfirst[nfirst % 10] + " "
        if (nfirst % 10 == 0 || nfirst % 100 in 11..19) rn += forms[2] + " "
        else when (nfirst % 10) {
            1 -> rn += forms[0] + " "
            in 2..4 -> rn += forms[1] + " "
            in 5..9 -> rn += forms[2] + " "
        }
    }
    if (nlast != 0) {
        if (nlast / 100 != 0) rn += hund[nlast / 100] + " "
        rn += if (nlast % 100 in 11..19) tens[nlast % 10] + " "
        else tens2[nlast / 10 % 10] + " "
        if (nlast % 10 != 0 && nlast % 100 !in 11..19) rn += diglast[nlast % 10]
    }
    return rn.replace("  ", " ").trim()
}