package com.example.arithmeticprogram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var result=0;
        var listOperation = findViewById<Spinner>(R.id.ListSelection);
        var operationOptions = arrayOf("Addition","Subtraction","Multiplication","Division","Modulo");
        var adapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,operationOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listOperation.adapter=adapter;
        var operand1= findViewById<EditText>(R.id.operand1);
        var operand2= findViewById<EditText>(R.id.operand2);
        var resultShow = findViewById<TextView>(R.id.result);
        var submitButton = findViewById<Button>(R.id.submit);
        var clearButton = findViewById<Button>(R.id.clear);
        var rotateButton = findViewById<Button>(R.id.rotateButton);
        var sentFirstButton = findViewById<Button>(R.id.resultSentFirst);
        var sentSecondButton = findViewById<Button>(R.id.resultSentSecond);

        submitButton.setOnClickListener {
            val selectedOperation = listOperation.selectedItem.toString();
            var number1 = operand1.text.toString().toInt();
            var number2 = operand2.text.toString().toInt();
            result = when (selectedOperation){
                "Addition" -> number1+number2
                "Subtraction" -> number1-number2
                "Multiplication" -> number1*number2
                "Division" -> number1/number2
                "Modulo" -> number1%number2
                else -> 0
            }
            resultShow.text= "Result : $result";
            Toast.makeText(this,"$result",Toast.LENGTH_LONG).show()
        }
        clearButton.setOnClickListener {
            operand1.setText("");
            operand2.setText("");
            Toast.makeText(this,"Cleared all Operands",Toast.LENGTH_LONG).show();
        }
        rotateButton.setOnClickListener {
            var temp = operand1.text;
            operand1.setText(operand2.text);
            operand2.setText(temp);
        }
        sentFirstButton.setOnClickListener {
            operand1.setText("$result");
        }

        sentSecondButton.setOnClickListener {
            operand2.setText("$result");
        }


    } }