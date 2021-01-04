package com.example.kcalculator

import com.example.kcalculator.CalculatorImpl.Companion.FORMATTING_ERROR
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class CalculatorImplTest {
    private lateinit var calculationManager: CalculatorImpl

    private val exception = Exception(FORMATTING_ERROR)

    @Before
    fun setUp() {
        calculationManager = CalculatorImpl()
    }

    @Test
    fun `when an input is provided with addition then calculate appropriately`() {
        val rawInput = "10 + 10 + 20"

        val result = calculationManager.calculate(rawInput)

        assertEquals(40, result.first)
        assertNull(result.second)
    }

    @Test
    fun `when an input is provided with mixed operator then calculate appropriately`() {
        val rawInput = "10 + 10 + 20 - 30"

        val result = calculationManager.calculate(rawInput)

        assertEquals(10, result.first)
        assertNull(result.second)
    }

    @Test
    fun `when an input is provided with add and subtract operator then calculate appropriately`() {
        val rawInput = "10 + 10 + 20 - 40"

        val result = calculationManager.calculate(rawInput)

        assertEquals(0, result.first)
        assertNull(result.second)
    }

    @Test
    fun `when an input is provided with add and divide operator then calculate appropriately`() {
        val rawInput = "10 + 10 + 20 / 40"

        val result = calculationManager.calculate(rawInput)

        assertEquals(1, result.first)
        assertNull(result.second)
    }

    @Test
    fun `when an input is provided with just one number then calculate appropriately`() {
        val rawInput = "10"

        val result = calculationManager.calculate(rawInput)

        assertEquals(10, result.first)
        assertNull(result.second)
    }

    @Test
    fun `when an input is provided with unsupported operator then assert exception`() {
        val rawInput = "10 + 10 + 20 ! 40"

        val result = calculationManager.calculate(rawInput)

        assertNull(result.first)
        assertEquals(exception.message, result.second?.message)
    }

    @Test
    fun `when an input is provided with unsupported characters then assert exception`() {
        val rawInput = "10 + 10 + 20e40"

        val result = calculationManager.calculate(rawInput)

        assertNull(result.first)
        assertEquals(exception.message, result.second?.message)
    }
}