package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.calculator.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val firstNumberText = StringBuilder("")
    private val secondNumberText = StringBuilder("")
    private val operatorNumberText = StringBuilder("")
    private val decimalFormat = DecimalFormat("#,###")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun numberClicked(view : View) {
        val numberString = (view as? Button)?.text.toString() ?: ""
        val numberText = if(operatorNumberText.isEmpty()) firstNumberText else secondNumberText
        numberText.append(numberString)
        updateEquationTextView()
    }

    fun clearClicked(view : View) {
        firstNumberText.clear()
        secondNumberText.clear()
        operatorNumberText.clear()

        updateEquationTextView()
        binding.resultTextview.text = ""
    }

    fun equalClicked(view : View) {
        if (firstNumberText.isEmpty() || secondNumberText.isEmpty() || operatorNumberText.isEmpty()) {
            Toast.makeText(this, "올바르지 않는 수식입니다.", Toast.LENGTH_SHORT).show()
            return
        }
        val firstNumber = firstNumberText.toString().toBigDecimal()
        val secondNumber = secondNumberText.toString().toBigDecimal()

        val result = when(operatorNumberText.toString()) {
            "+" -> decimalFormat.format(firstNumber + secondNumber)
            "-" -> decimalFormat.format(firstNumber - secondNumber)
            else -> "잘못된 수식입니다."
        }
        // decimal format의 결과값은 스트링 형태
        binding.resultTextview.text = result
    }

    fun operatorClicked(view : View) {
        val operatorString = (view as? Button)?.text.toString() ?: ""

        if (firstNumberText.isEmpty()) {
            Toast.makeText(this, "먼저 숫자를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (secondNumberText.isNotEmpty()) {
            Toast.makeText(this, "1개의 연산자에 있어서만 연산이 가능합니다.", Toast.LENGTH_SHORT).show()
            return
        }

        operatorNumberText.append(operatorString)
        updateEquationTextView()
    }

    // UI 업데이트
    private fun updateEquationTextView() {
        // decimalFormat 처리
        val firstFormattedNumber = if (firstNumberText.isNotEmpty()) decimalFormat.format(firstNumberText.toString().toBigDecimal()) else ""
        val secondFormattedNumber = if (secondNumberText.isNotEmpty()) decimalFormat.format(secondNumberText.toString().toBigDecimal()) else ""
        binding.equationiTextview.text = "$firstFormattedNumber $operatorNumberText $secondFormattedNumber"
    }

}