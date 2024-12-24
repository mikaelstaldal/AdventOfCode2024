import kotlin.time.measureTime

fun parseEquation(s: String): Pair<Long, List<Long>> {
    val testValue = s.substring(0, s.indexOf(":")).toLong()
    val numbers = s.substring(s.indexOf(":") + 2).split(" ").map { it.toLong() }
    return testValue to numbers
}

fun apply(left: Long, op: Long, right: Long): Long = when (op) {
    0L -> left + right
    1L -> left * right
    2L -> (left.toString() + right.toString()).toLong()
    else -> throw IllegalArgumentException("Unknown op $op")
}

fun eval(exp: List<Long>): Long {
    var result = apply(exp[0], exp[1], exp[2])
    for (i in 3..<exp.size step 2) {
        result = apply(result, exp[i], exp[i + 1])
    }
    return result
}

fun generateCandidatesA(numbers: List<Long>): List<List<Long>> = if (numbers.size < 2)
    listOf(numbers)
else generateCandidatesA(numbers.drop(1)).map { listOf(numbers[0], 0) + it } +
        generateCandidatesA(numbers.drop(1)).map { listOf(numbers[0], 1) + it }

fun generateCandidatesB(numbers: List<Long>): List<List<Long>> = if (numbers.size < 2)
    listOf(numbers)
else generateCandidatesB(numbers.drop(1)).map { listOf(numbers[0], 0) + it } +
        generateCandidatesB(numbers.drop(1)).map { listOf(numbers[0], 1) + it } +
        generateCandidatesB(numbers.drop(1)).map { listOf(numbers[0], 2) + it }

fun day7a(input: List<String>) {
    val equations = input.map { parseEquation(it) }
    var sum = 0L
    for ((testValue, numbers) in equations) {
        for (candidate in generateCandidatesA(numbers)) {
            if (eval(candidate) == testValue) {
                sum += testValue
                break
            }
        }
    }
    println(sum)
}

fun day7b(input: List<String>) {
    val equations = input.map { parseEquation(it) }
    var sum = 0L
    for ((testValue, numbers) in equations) {
        for (candidate in generateCandidatesB(numbers)) {
            if (eval(candidate) == testValue) {
                sum += testValue
                break
            }
        }
    }
    println(sum)
}

fun main() {
    val time = measureTime {
        day7b(
            listOf(
                "190: 10 19",
                "3267: 81 40 27",
                "83: 17 5",
                "156: 15 6",
                "7290: 6 8 6 15",
                "161011: 16 10 13",
                "192: 17 8 14",
                "21037: 9 7 18 13",
                "292: 11 6 16 20",
            )
        )
    }
    println("Took: $time")
}
