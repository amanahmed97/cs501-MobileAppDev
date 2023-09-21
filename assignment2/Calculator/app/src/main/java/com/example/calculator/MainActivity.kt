package com.example.calculator

import android.os.Bundle
import android.text.InputType
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Stack
import java.text.DecimalFormat
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var b0 = findViewById<Button>(R.id.btn0);
        var b1 = findViewById<Button>(R.id.btn1);
        var b2 = findViewById<Button>(R.id.btn2);
        var b3 = findViewById<Button>(R.id.btn3);
        var b4 = findViewById<Button>(R.id.btn4);
        var b5 = findViewById<Button>(R.id.btn5);
        var b6 = findViewById<Button>(R.id.btn6);
        var b7 = findViewById<Button>(R.id.btn7);
        var b8 = findViewById<Button>(R.id.btn8);
        var b9 = findViewById<Button>(R.id.btn9);
        var add= findViewById<Button>(R.id.addition);
        var subtract= findViewById<Button>(R.id.subtraction);
        var divide = findViewById<Button>(R.id.Division);
        var multiply = findViewById<Button>(R.id.multiply);
        var calculateResult = findViewById<Button>(R.id.Calculate);
        var decimalVal = findViewById<Button>(R.id.Decimal);
        var clear = findViewById<Button>(R.id.btnAC);
        var backspace = findViewById<Button>(R.id.backspace);
        var squareRoot = findViewById<Button>(R.id.root);
        var calculationView  = findViewById<EditText>(R.id.Calculation);
        var resultView = findViewById<TextView>(R.id.result);

        val decimalFormat = DecimalFormat("#.####")

        b0.setOnClickListener {
            calculationView.setText(calculationView.text.toString()+"0");
        }
        b1.setOnClickListener {
            calculationView.setText(calculationView.text.toString()+"1")
        }
        b2.setOnClickListener {
            calculationView.setText(calculationView.text.toString()+"2")
        }
        b3.setOnClickListener {
            calculationView.setText(calculationView.text.toString()+"3")
        }
        b4.setOnClickListener {
            calculationView.setText(calculationView.text.toString()+"4")
        }
        b5.setOnClickListener {
            calculationView.setText(calculationView.text.toString()+"5")
        }
        b6.setOnClickListener {
            calculationView.setText(calculationView.text.toString()+"6")
        }
        b7.setOnClickListener {
            calculationView.setText(calculationView.text.toString()+"7")
        }
        b8.setOnClickListener {
            calculationView.setText(calculationView.text.toString()+"8")
        }
        b9.setOnClickListener {
            calculationView.setText(calculationView.text.toString()+"9")
        }
        subtract.setOnClickListener {
            calculationView.setText(calculationView.text.toString()+"-")


        }
        add.setOnClickListener {
            if(calculationView.text.toString() !=""){
                calculationView.setText(calculationView.text.toString()+"+")
            }
            else{
                Toast.makeText(this,"Please enter the operands for the respective operator!", Toast.LENGTH_LONG).show()
            }

        }
        divide.setOnClickListener {
            if(calculationView.text.toString() !=""){
                calculationView.setText(calculationView.text.toString()+"/")
            }
            else{
                Toast.makeText(this,"Please enter the operands for the respective operator!", Toast.LENGTH_LONG).show()
            }

        }
        multiply.setOnClickListener {
            if(calculationView.text.toString() !=""){
                calculationView.setText(calculationView.text.toString()+"*")
            }
            else{
                Toast.makeText(this,"Please enter the operands for the respective operator!", Toast.LENGTH_LONG).show()
            }

        }
        decimalVal.setOnClickListener {
            calculationView.setText(calculationView.text.toString()+".")

        }
        calculateResult.setOnClickListener {
            if (calculationView.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter a valid number!", Toast.LENGTH_LONG).show()
            } else {
                val str: String = calculationView.text.toString()
                val result = evaluateInput(str)
                val finalResult = result.toString()
                resultView.setText(finalResult)
            }
        }
        clear.setOnClickListener {
            calculationView.setText("")
            resultView.setText("")
        }
        backspace.setOnClickListener {
            if(calculationView.text.isNotEmpty()){
                val updatedText = calculationView.text.substring(0,calculationView.text.length-1);
                calculationView.setText(updatedText);
            }
        }
        squareRoot.setOnClickListener {
            val sqstr = calculationView.text.toString()
            if (sqstr.isEmpty()) {
                Toast.makeText(this, "Please enter a valid number!", Toast.LENGTH_LONG).show()
            } else {
                try {
                    val result = evaluateInput(sqstr)
                    val sqrtResult = Math.sqrt(result)
                    val finalResult = sqrtResult.toString()
                    resultView.setText(finalResult)
                } catch (e: Exception) {
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        calculateResult.setOnClickListener {
            val input = calculationView.text.toString()

            if (input.isEmpty() || !input.matches(Regex("[0-9+\\-*/.]+"))) {
                Toast.makeText(this, "Invalid expression.", Toast.LENGTH_LONG).show()
            } else if (input.contains("..") || input.contains("++") || input.contains("--") || input.contains("///") || input.contains("**")) {
                Toast.makeText(this, "Invalid input: Multiple consecutive operators or decimal points!", Toast.LENGTH_LONG).show()
            } else if (input.contains("/0")) {
                Toast.makeText(this, "Division by zero is not allowed!", Toast.LENGTH_LONG).show()
            } else {
                val result = evaluateInput(input)
                val formattedResult = decimalFormat.format(result)
                resultView.text = formattedResult
            }
        }


        calculationView.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_EQUALS) {
                val inputExpression = calculationView.text.toString()

                // Find and process square root expressions and other operators
                val pattern = Pattern.compile("sqrt\\(([^)]+)\\)|([-+*/])|([0-9.]+)")
                val matcher = pattern.matcher(inputExpression)
                val result = StringBuffer()

                while (matcher.find()) {
                    val match = matcher.group()
                    if (match.startsWith("sqrt(")) {
                        val innerExpression = match.substring(5, match.length - 1)
                        try {
                            val innerResult = Math.sqrt(evaluateInput(innerExpression)).toString()
                            matcher.appendReplacement(result, innerResult)
                        } catch (e: Exception) {
                            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                            return@setOnKeyListener true // Consume the key event
                        }
                    } else {
                        matcher.appendReplacement(result, match)
                    }
                }

                matcher.appendTail(result)
                val processedExpression = result.toString()

                // Now, evaluate the processed expression
                try {
                    val finalResult = evaluateInput(processedExpression).toString()
                    resultView.setText(finalResult)
                } catch (e: Exception) {
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }

                true // Consume the key event
            } else {
                false // Return false to allow normal key handling
            }
        }

// Allow operators and numbers to be entered in the calculationView
        calculationView.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE



    }

    fun evaluateInput(expression: String): Double {
        val expression = "0${expression}"
        val operands = Stack<Double>()
        val operators = Stack<Char>()
        var i = 0
        while (i < expression.length) {
            val ch = expression[i]

            if (ch == ' ') {
                i++
                continue
            }

            if (Character.isDigit(ch) || (ch == '-' && (i == 0 || expression[i - 1] == '+' || expression[i - 1] == '-'))) {
                val isDecimal = (ch == '.')
                var numStr = ""
                while (i < expression.length && (Character.isDigit(expression[i]) || (!isDecimal && expression[i] == '.'))) {
                    numStr += expression[i]
                    i++
                }
                i--
                val num = numStr.toDouble()

                if (num > Double.MAX_VALUE || num < -Double.MAX_VALUE) {
                    Toast.makeText(this, "Operand out of range", Toast.LENGTH_LONG).show()
                    return 0.0
                }

                operands.push(num)
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                // Handle unary minus
                if (ch == '-' && (i == 0 || expression[i - 1] == '+' || expression[i - 1] == '-')) {
                    val isDecimal = (ch == '.')
                    var numStr = "-"
                    i++
                    while (i < expression.length && (Character.isDigit(expression[i]) || (!isDecimal && expression[i] == '.'))) {
                        numStr += expression[i]
                        i++
                    }
                    i--
                    val num = numStr.toDouble()

                    // Check for double bounds
                    if (num > Double.MAX_VALUE || num < -Double.MAX_VALUE) {
                        Toast.makeText(this, "Operand out of range", Toast.LENGTH_LONG).show()
                        return 0.0
                    }

                    operands.push(num)
                } else {
                    while (!operators.empty() && hasPrecedence(ch, operators.peek())) {
                        operands.push(applyOperation(operators.pop(), operands.pop(), operands.pop()))
                    }
                    operators.push(ch)
                }
            }
            i++
        }
        while (!operators.empty()) {
            operands.push(applyOperation(operators.pop(), operands.pop(), operands.pop()))
        }
        val result = operands.pop()

        if (result > Int.MAX_VALUE || result < Int.MIN_VALUE) {
            Toast.makeText(this, "Result out of range for Int", Toast.LENGTH_LONG).show()
            return 0.0
        }

        return result
    }

    fun hasPrecedence(op1: Char, op2: Char): Boolean {
        return !((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
    }

    fun applyOperation(operator: Char, operand2: Double, operand1: Double): Double {
        when (operator) {
            '+' -> return operand1 + operand2
            '-' -> return operand1 - operand2
            '*' -> return operand1 * operand2
            '/' -> return operand1 / operand2
        }
        return 0.0
    }



}

/*
Referred online resources such as YouTube,GeekForGeeks and ChatGPT for understanding.
 */