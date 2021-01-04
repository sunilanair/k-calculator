package com.example.kcalculator

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val viewModel: MainViewModel = MainViewModel()
    private val calculator = mockk<Calculator>(relaxed = true)

    @Before
    fun setUp() {
        viewModel.calculator = calculator
    }

    @Test
    fun `when updateView is invoked with value then emit the value and assert that the calculation input is updated`() {
        viewModel.updateView("1")

        assertTrue(viewModel.calculationInput == "1")
        assertTrue(viewModel.updateViewEvent.value == "1")
    }

    @Test
    fun `when undo is invoked with value then emit the value by dropping the last char from the provided input string`() {
        viewModel.calculationInput = "12"

        viewModel.undo()

        assertTrue(viewModel.updateViewEvent.value == "1")
    }

    @Test
    fun `when reset is invoked with value then assert that value emitted and calculation input are empty`() {
        viewModel.calculationInput = "12"

        viewModel.reset()

        assertTrue(viewModel.calculationInput == "")
        assertTrue(viewModel.updateViewEvent.value == "")
    }

    @Test
    fun `when getResult is invoked then verify calculator is called`() {
        viewModel.getResult()

        verify { calculator.calculate(any()) }
    }

    @Test
    fun `when getResult is invoked and calculator provides a result then assert the emitted result`() {
        val result = Pair(12, null)
        every { calculator.calculate(any()) } returns result

        viewModel.getResult()

        assertTrue(viewModel.resultEvent.value == result)
    }
}