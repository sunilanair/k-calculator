package com.example.kcalculator

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.kcalculator.common.SingleLiveEvent
import java.lang.Exception

class MainViewModel : ViewModel() {
    var calculationInput: String? = ""
        set(value) {
            field = value
        }

    private val mutableUpdateViewEvent = SingleLiveEvent<String>()
    val updateViewEvent: LiveData<String> get() = mutableUpdateViewEvent

    private val mutableResultEvent = SingleLiveEvent<Pair<Int?, Exception?>>()
    val resultEvent: LiveData<Pair<Int?, Exception?>> get() = mutableResultEvent

    @VisibleForTesting
    var calculator: Calculator? = CalculatorImpl()

    fun updateView(i: String) {
        calculationInput += i
        mutableUpdateViewEvent.postValue(calculationInput)
    }

    fun getResult() {
        val result = calculator?.calculate(calculationInput)
        mutableResultEvent.postValue(result)
    }

    fun undo() {
        calculationInput = calculationInput?.dropLast(1)
        mutableUpdateViewEvent.postValue(calculationInput)
    }

    fun reset() {
        calculationInput = ""
        mutableUpdateViewEvent.postValue(calculationInput)
    }
}