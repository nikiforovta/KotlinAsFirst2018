@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import lesson3.task1.digitNumber
import java.io.File

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val res = mutableMapOf<String, Int>()
    for (substring in substrings) {
        res[substring] = res[substring] ?: 0
    }
    for (line in File(inputName).readLines()) {
        for (word in line.split(" ")) for (it in substrings.toSet()) {
            if (it.toLowerCase() in word.toLowerCase()) when {
                it.length == 1 -> res[it] = (res[it] ?: 0) + word.count { a -> a.toLowerCase() == it[0].toLowerCase() }
                it.length == word.length -> res[it] = (res[it] ?: 0) + 1
                else -> for (a in 0 until word.length - it.length + 1)
                    if (word.substring(a, a + it.length).toLowerCase() == it.toLowerCase()) res[it] = (res[it] ?: 0) + 1
            }
        }
    }
    return res
}

/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun grammarChange(word: String, letter: Char, i: IntRange): String {
    return when (word[i.first]) {
        'ы' -> word.replaceRange(i, "и")
        'я' -> word.replaceRange(i, "а")
        'ю' -> word.replaceRange(i, "у")
        'Ы' -> word.replaceRange(i, "И")
        'Я' -> word.replaceRange(i, "А")
        'Ю' -> word.replaceRange(i, "У")
        else -> word
    }
}

fun sibilants(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    val inputStream = File(inputName).readText().split(" ")
    for ((i, word) in inputStream.withIndex()) {
        var replaceWord = word
        for (letter in Regex("""((?<=[жшчщ])[ыяю])""").findAll(word.toLowerCase()))
            replaceWord = grammarChange(replaceWord, letter.value.single(), letter.range)
        outputStream.write(replaceWord)
        if (i != inputStream.lastIndex) outputStream.write(" ")
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    val lines = File(inputName).readLines().map { it.trim() }
    val longestLineLength = (lines.maxBy { it.length } ?: "").length
    for ((i, line) in lines.withIndex()) {
        val currentLineLength = line.length
        if (longestLineLength > currentLineLength)
            outputStream.write(" ".repeat((longestLineLength - currentLineLength) / 2))
        outputStream.write(line)
        if (i < lines.size) outputStream.newLine()
    }
    outputStream.close()
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    val longestLineLength = (File(inputName).readLines().map { it.trim() }.maxBy { it.length } ?: "").length
    for (line in File(inputName).readLines().map { it.trim() }) {
        val words = Regex("""\s+""").split(line)
        val lastIndex = words.lastIndex
        if (lastIndex < 1 || words.joinToString(" ").length == longestLineLength)
            outputStream.write(line)
        else {
            val space = longestLineLength - words.joinToString("").length
            val everySpace = space / lastIndex
            val rest = space % lastIndex
            for ((index, word) in words.withIndex())
                outputStream.write(word + if (index != lastIndex) " ".repeat(everySpace + if (index < rest) 1 else 0) else "")
        }
        outputStream.newLine()
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val res = mutableMapOf<String, Int>()
    for (line in File(inputName).readLines()) {
        val words = mutableListOf<String>()
        for (el in Regex("""[а-яёa-z]+""").findAll(line.toLowerCase())) words += el.value
        val uniqueWord = words.toSet().toList()
        val pres = mutableMapOf<String, Int>()
        for (word in uniqueWord) pres[word] = words.count { currentWord -> currentWord == word }
        for ((key, value) in pres) res[key] = (res[key] ?: 0) + value
    }
    return res.toList().sortedByDescending { it.second }.take(20).toMap()
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val read = File(inputName).reader()
    val fullDictionary = mutableMapOf<Char, String>()
    for ((key, value) in dictionary) {
        val valFromFullDict = fullDictionary[key.toLowerCase()] ?: ""
        fullDictionary[key.toLowerCase()] = valFromFullDict + value.toLowerCase()
        if (key.toLowerCase() != key.toUpperCase())
            fullDictionary[key.toUpperCase()] = valFromFullDict + value.toLowerCase().capitalize()
    }
    val outputStream = File(outputName).bufferedWriter()
    var charInt = read.read()
    while (charInt != -1) {
        if (fullDictionary.keys.contains(charInt.toChar())) outputStream.write(fullDictionary[charInt.toChar()])
        else outputStream.write(charInt.toChar().toString())
        charInt = read.read()
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val res = mutableMapOf<Int, MutableList<String>>()
    var chaoticWordLength = 0
    try {
        for (line in File(inputName).readLines()) {
            if (line.isEmpty()) continue
            val letters = mutableListOf<String>()
            for (el in Regex("""[а-яёa-z]""").findAll(line.toLowerCase())) letters += el.value
            val uniqueLettersSize = letters.toSet().toList().size
            if (letters.size == uniqueLettersSize) {
                res.getOrPut(uniqueLettersSize) { mutableListOf() }.add(line.trim())
                if (uniqueLettersSize > chaoticWordLength) chaoticWordLength = uniqueLettersSize
            }
        }
        File(outputName).bufferedWriter().apply {
            write(res[chaoticWordLength]!!.joinToString())
            close()
        }
    } catch (e: NullPointerException) {
        File(outputName).writeText("")
    }
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    val multiElements = mutableListOf<Int>()
    var multiplier = rhv
    while (multiplier != 0) {
        multiElements.add(lhv * (multiplier % 10))
        multiplier /= 10
    }
    val fullLine = (lhv * rhv).toString().length + 1
    outputStream.write(" ".repeat(fullLine - digitNumber(lhv)) + lhv.toString() + "\n" + "*" +
            " ".repeat(fullLine - digitNumber(rhv) - 1) + rhv.toString() + "\n" + "-".repeat(fullLine) +
            "\n" + " ".repeat(fullLine - digitNumber(multiElements[0])) + multiElements[0].toString() + "\n")
    if (rhv / 10 != 0) {
        for (i in 1 until multiElements.size) {
            outputStream.write("+" + " ".repeat(fullLine - digitNumber(multiElements[i]) - (i + 1)) +
                    multiElements[i].toString() + "\n")
        }
    }
    outputStream.write("-".repeat(fullLine) + "\n" + " " + (lhv * rhv).toString())
    outputStream.close()
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var subtrahend = 0
    var i = 0
    if (lhv < rhv) {
        val spaces = if (digitNumber(lhv) > 1) 0 else 1
        outputStream.write(" ".repeat(spaces) + "$lhv | $rhv\n")
        outputStream.write(" ".repeat(spaces + digitNumber(lhv) - 2) + "-0" + "   0\n" +
                " ".repeat(minOf(spaces, digitNumber(lhv) - 1)) + "-".repeat(maxOf(2, digitNumber(lhv))) + "\n" +
                " ".repeat(spaces) + lhv.toString())
        outputStream.close()
    } else {
        while (subtrahend < rhv) {
            subtrahend = subtrahend * 10 + lhv.toString()[i].toString().toInt()
            i++
        }
        var restOutput = subtrahend.toString()
        var subtractor = subtrahend / rhv * rhv
        var spaces = if (digitNumber(subtrahend) > digitNumber(subtractor)) 0 else 1
        outputStream.write(" ".repeat(spaces) + "$lhv | $rhv")
        var rest: Int
        for (k in 1..digitNumber(lhv / rhv)) {
            subtractor = subtrahend / rhv * rhv
            spaces += restOutput.length - digitNumber(subtractor) - 1
            if (k == 1) {
                val secondSpaces = digitNumber(lhv) + 3 - restOutput.length
                outputStream.write("\n" + " ".repeat(spaces) + "-$subtractor" +
                        " ".repeat(secondSpaces) + "${(lhv / rhv)}")
            } else outputStream.write("\n" + " ".repeat(spaces) + "-$subtractor")
            val underline = maxOf(digitNumber(subtrahend), digitNumber(subtractor) + 1)
            val spaceUnderline = minOf(spaces + 1 + digitNumber(subtractor) - digitNumber(subtrahend), spaces)
            outputStream.write("\n" + " ".repeat(spaceUnderline) + "-".repeat(underline))
            rest = subtrahend - subtractor
            restOutput = if (i < digitNumber(lhv)) rest.toString() + lhv.toString()[i].toString()
            else rest.toString()
            i++
            val spaceRest = spaceUnderline + underline - digitNumber(rest)
            outputStream.write("\n" + " ".repeat(spaceRest) + restOutput)
            subtrahend = restOutput.toInt()
            spaces = spaceRest

        }
        outputStream.close()
    }
}