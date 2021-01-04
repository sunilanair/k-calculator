package com.example.kcalculator

interface Calculator {
    fun calculate(input: String?): Pair<Int?, Exception?>
}