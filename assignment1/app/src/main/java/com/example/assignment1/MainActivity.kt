package com.example.assignment1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myNameTextView = findViewById<TextView>(R.id.myNameTextView)
        var myName = "Aman"
        val text = getString(R.string.my_name, myName)
        myNameTextView.text = text

    }
}