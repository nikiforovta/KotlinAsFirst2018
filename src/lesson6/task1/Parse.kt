@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val parts = str.split(" ")
    if (parts.size != 3) return ""
    else {
        val month = when (parts[1]) {
            "января" -> "01"
            "февраля" -> "02"
            "марта" -> "03"
            "апреля" -> "04"
            "мая" -> "05"
            "июня" -> "06"
            "июля" -> "07"
            "августа" -> "08"
            "сентября" -> "09"
            "октября" -> "10"
            "ноября" -> "11"
            "декабря" -> "12"
            else -> ""
        }
        return if (month == "" || daysInMonth(month.toInt(), parts[2].toInt()) < parts[0].toInt()) ""
        else String.format("%02d.%s.%s", parts[0].toInt(), month, parts[2])
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val parts = digital.split(".")
    if (parts.size != 3) return ""
    else {
        val month = when (parts[1]) {
            "01" -> "января"
            "02" -> "февраля"
            "03" -> "марта"
            "04" -> "апреля"
            "05" -> "мая"
            "06" -> "июня"
            "07" -> "июля"
            "08" -> "августа"
            "09" -> "сентября"
            "10" -> "октября"
            "11" -> "ноября"
            "12" -> "декабря"
            else -> ""
        }
        return if (month == "" || daysInMonth(parts[1].toInt(), parts[2].toInt()) < parts[0].toInt()) ""
        else String.format("%d %s %s", parts[0].toInt(), month, parts[2])
    }
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String {
    if (!phone.matches(Regex("""^(\+\s*\d)?([\d\s\-()])*""")) ||
            phone.contains(Regex("""\n|\r|\t|\v|\f"""))) return ""
    val parts = phone.split(" ", "-", "(", ")")
    return parts.joinToString(separator = "")
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val parts = jumps.split(Regex("""( )+"""))
    var longJump = -1
    parts.forEach {
        if (!it.contains(Regex("[0-9]|[-%]"))) return -1
        if (it.toIntOrNull() != null) {
            if (it.toInt() > longJump) longJump = it.toInt()
        }
    }
    return longJump
}


/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    val parts = jumps.split(" ")
    var highJump = -1
    parts.forEachIndexed { index, it ->
        if (!it.contains(Regex("[0-9]|[-+%]"))) return -1
        if (it.toIntOrNull() != null && parts[index + 1].matches(Regex("""(%)*\+"""))) {
            if (it.toInt() > highJump) highJump = it.toInt()
        }
    }
    return highJump
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    val parts = expression.split(" ")
    if (parts[0].toIntOrNull() == null) throw IllegalArgumentException() else {
        var res = parts[0].toInt()
        when {
            parts.size == 1 -> if (parts[0].contains(Regex("([+\\-])([0-9])|([0-9])([+\\-])")))
                throw IllegalArgumentException()
            else return parts[0].toInt()
            else -> (1 until parts.size).forEach { i ->
                if (parts[i - 1].toIntOrNull() != null && parts[i].toIntOrNull() != null
                        || parts[i - 1].toIntOrNull() == null && parts[i].toIntOrNull() == null
                        || Regex("([+\\-])([0-9])|([0-9])([+\\-])") in parts[i - 1]
                        || Regex("([+\\-])([0-9])|([0-9])([+\\-])") in parts[i])
                    throw IllegalArgumentException()
                if (parts[i].toIntOrNull() != null) if (parts[i - 1] == "+") res += parts[i].toInt()
                else res -= parts[i].toInt()
            }
        }
        return res
    }
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    var res = -1
    val parts = str.split(" ")
    if (parts.size > 1) (0 until parts.size - 1).forEach {
        res += parts[it].length.plus(1)
        if (parts[it].toLowerCase() == parts[it + 1].toLowerCase()) return res - parts[it].length
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    var res = ""
    var price = -1.0
    val parts = description.split(" ", "; ")
    for (i in 1 until parts.size) {
        if (parts[i].toDoubleOrNull() != null)
            if (parts[i].toDouble() > price) {
                price = parts[i].toDouble()
                res = parts[i - 1]
            }
    }
    return res
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    var plusString = "0 "
    val rom = listOf("", "I", "V", "X", "L", "C", "D", "M")
    val parts = roman.split("")
    (1 until parts.size - 1).forEach { i ->
        if (rom.contains(parts[i]) && rom.contains(parts[i + 1])) {
            plusString += when {
                rom.indexOf(parts[i]) < rom.indexOf(parts[i + 1]) -> "- "
                else -> "+ "
            }
            plusString += when (parts[i]) {
                "I" -> "1 "
                "V" -> "5 "
                "X" -> "10 "
                "L" -> "50 "
                "C" -> "100 "
                "D" -> "500 "
                "M" -> "1000 "
                else -> ""
            }
        } else return -1
    }
    return if (plusString == "0 ") -1 else plusMinus(plusString.trim())
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    val parts = commands.split("").toMutableList()
    for (it in 0 until parts.size - 1) {
        parts[it] = parts[it + 1]
    }
    parts.removeAt(parts.size - 2)
    parts.removeAt(parts.size - 1)
    var i = cells / 2
    var command = 0
    var stopLimit = limit
    var cycleCommand = 0

    var checkForException = 0
    Regex("""[\[\]]""").findAll(commands).forEach { it ->
        if (it.value == "[") checkForException++ else checkForException--
        if (checkForException < 0) throw IllegalArgumentException()
    }
    if (checkForException > 0) throw IllegalArgumentException()
    val result = mutableListOf<Int>()
    (0 until cells).forEach { it ->
        result.add(it, 0)
    }

    while (stopLimit != 0 && command < commands.length) {
        when (parts[command]) {
            "+" -> {
                result[i]++
                command++
                stopLimit--
            }
            "-" -> {
                result[i]--
                command++
                stopLimit--
            }
            ">" -> {
                i++
                command++
                stopLimit--
            }
            "<" -> {
                i--
                command++
                stopLimit--
            }
            " " -> {
                command++
                stopLimit--
            }
            "[" -> {
                if (result[i] == 0) {
                    cycleCommand--
                    do {
                        command++
                        when (commands[command]) {
                            '[' -> cycleCommand--
                            ']' -> cycleCommand++
                        }
                    } while (cycleCommand != 0)
                }
                command++
                stopLimit--
            }
            "]" -> {
                if (result[i] != 0) {
                    cycleCommand++
                    do {
                        command--
                        when (commands[command]) {
                            '[' -> cycleCommand--
                            ']' -> cycleCommand++
                        }
                    } while (cycleCommand != 0)
                }
                command++
                stopLimit--
            }
            else -> throw IllegalArgumentException()
        }
        if (i !in 0 until cells) throw IllegalStateException()
    }
    return result
}
