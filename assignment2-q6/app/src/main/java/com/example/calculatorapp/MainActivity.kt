package com.example.calculatorapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Stack


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
        var resultView = findViewById<EditText>(R.id.result);
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
            if(calculationView.text.toString().isEmpty()){
                Toast.makeText(this,"Please enter a valid number !",Toast.LENGTH_LONG ).show();

            }else{
                try {
                    val str: String = calculationView.text.toString()
                    val result = evaluate(str)
                    val finalResult = result.toString();
                    resultView.setText(finalResult);
                }catch(e: Exception){
                    Toast.makeText(this,"Please enter a valid number !",Toast.LENGTH_LONG ).show();
                }
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
            if(sqstr.isEmpty()){
                Toast.makeText(this,"Please enter a valid number !",Toast.LENGTH_LONG ).show();

            }else if (sqstr.contains('+') || sqstr.contains('-') || sqstr.contains('*') || sqstr.contains('/')){
                Toast.makeText(this,"Please enter a valid number !",Toast.LENGTH_LONG ).show();

            }else{
                val str: String = calculationView.text.toString()
                val result = Math.sqrt(str.toDouble());
                val finalResult = result.toString();
                resultView.setText(finalResult);

            }
        }


    }


    fun evaluate(expression: String): Double {
        // Create a stack to hold operands
        val operands = Stack<Double>()

        // Create a stack to hold operators
        val operators = Stack<Char>()
        var i = 0
        while (i < expression.length) {
            val ch = expression[i]

            // If the current character is a whitespace, skip it
            if (ch == ' ') {
                i++
                continue
            }

            // If the current character is a digit, push it to the operand stack
            if (Character.isDigit(ch)) {
                var num = 0
                while (i < expression.length && Character.isDigit(expression[i])) {
                    num = num * 10 + Character.getNumericValue(expression[i])
                    i++
                }
                i--
                operands.push(num.toDouble())
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                while (!operators.empty() && hasPrecedence(ch, operators.peek())) {
                    operands.push(applyOperation(operators.pop(), operands.pop(), operands.pop()))
                }
                operators.push(ch)
            }
            i++
        }
        while (!operators.empty()) {
            operands.push(applyOperation(operators.pop(), operands.pop(), operands.pop()))
        }
        return operands.pop()
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