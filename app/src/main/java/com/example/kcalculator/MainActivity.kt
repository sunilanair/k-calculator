package com.example.kcalculator

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.displayTextView
import kotlinx.android.synthetic.main.activity_main.undoButton
import kotlinx.android.synthetic.main.view_number_pad.eightButton
import kotlinx.android.synthetic.main.view_number_pad.fiveButton
import kotlinx.android.synthetic.main.view_number_pad.fourButton
import kotlinx.android.synthetic.main.view_number_pad.nineButton
import kotlinx.android.synthetic.main.view_number_pad.oneButton
import kotlinx.android.synthetic.main.view_number_pad.sevenButton
import kotlinx.android.synthetic.main.view_number_pad.sixButton
import kotlinx.android.synthetic.main.view_number_pad.threeButton
import kotlinx.android.synthetic.main.view_number_pad.twoButton
import kotlinx.android.synthetic.main.view_number_pad.zeroButton
import kotlinx.android.synthetic.main.view_operator_pad.addButton
import kotlinx.android.synthetic.main.view_operator_pad.divisionButton
import kotlinx.android.synthetic.main.view_operator_pad.minusButton
import kotlinx.android.synthetic.main.view_operator_pad.multiplicationButton
import kotlinx.android.synthetic.main.view_operator_pad.resetButton
import kotlinx.android.synthetic.main.view_operator_pad.resultButton
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setNumberPadClickListeners()
        setOperatorPadClickListeners()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.updateViewEvent.observe(this, ::updateView)
        viewModel.resultEvent.observe(this, ::renderResult)
    }

    private fun renderResult(result: Pair<Int?, Exception?>) {
        if (result.second != null) {
            Toast.makeText(this, getString(R.string.unsupported_text), Toast.LENGTH_SHORT).show()
        } else {
            val actualResult = result.first
            actualResult?.let {
                displayTextView.text = it.toString()
                viewModel.calculationInput = it.toString()
            }
        }
    }

    private fun updateView(value: String?) {
        displayTextView.text = value
    }

    private fun setOperatorPadClickListeners() {
        addButton.setOnClickListener { viewModel.updateView(" + ") }
        minusButton.setOnClickListener { viewModel.updateView(" - ") }
        multiplicationButton.setOnClickListener { viewModel.updateView(" * ") }
        divisionButton.setOnClickListener { viewModel.updateView(" / ") }
        resultButton.setOnClickListener { viewModel.getResult() }
        undoButton.setOnClickListener { viewModel.undo() }
        resetButton.setOnClickListener { viewModel.reset() }
    }

    private fun setNumberPadClickListeners() {
        oneButton.setOnClickListener { viewModel.updateView("1") }
        twoButton.setOnClickListener { viewModel.updateView("2") }
        threeButton.setOnClickListener { viewModel.updateView("3") }
        fourButton.setOnClickListener { viewModel.updateView("4") }
        fiveButton.setOnClickListener { viewModel.updateView("5") }
        sixButton.setOnClickListener { viewModel.updateView("6") }
        sevenButton.setOnClickListener { viewModel.updateView("7") }
        eightButton.setOnClickListener { viewModel.updateView("8") }
        nineButton.setOnClickListener { viewModel.updateView("9") }
        zeroButton.setOnClickListener { viewModel.updateView("0") }
    }
}