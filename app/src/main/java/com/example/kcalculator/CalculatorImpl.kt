package com.example.kcalculator

class CalculatorImpl : Calculator {

    private var operatorList = listOf("+", "-", "/", "*")

    override fun calculate(input: String?): Pair<Int?, Exception?> {
        var result: Int? = null
        var error: Exception? = null
        var currentOperator: String? = null
        input?.let { rawInput ->
            val inputArray = rawInput.split(" ").map { it.trim() }
            inputArray.forEach { input ->
                if (isSupportedOperator(input)) {
                    currentOperator = input
                } else {
                    try {
                        result = result?.let {
                            currentOperator?.let { operator ->
                                calculateByOperator(operator).invoke(it, input.toInt())
                            }
                        } ?: input.toInt()
                    } catch (ex: NumberFormatException) {
                        error = NumberFormatException(FORMATTING_ERROR)
                        return Pair(null, error)
                    }
                }
            }
        }
        return Pair(result, error)
    }

    // private fun isNumeric(input: String) = input.matches("-?\\d+(\\.\\d+)?".toRegex())

    private fun isSupportedOperator(input: String) = operatorList.contains(input)

    private fun calculateByOperator(operator: String): (Int, Int) -> Int {
        return when (operator) {
            "+" -> { a, b -> a + b }
            "-" -> { a, b -> a - b }
            "/" -> { a, b -> a / b }
            "*" -> { a, b -> a * b }
            else -> throw Exception("That's not a supported operator")
        }
    }

    companion object {
        const val FORMATTING_ERROR = "Number Formatting error"
    }
}