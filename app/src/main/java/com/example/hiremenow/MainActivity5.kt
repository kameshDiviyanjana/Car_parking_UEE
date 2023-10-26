package com.example.hiremenow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity5 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        val NEXT = findViewById<Button>(R.id.cancelconfirm)
        NEXT.setOnClickListener {
            val intent = Intent(this, MainActivity13::class.java) // image view as a button
            startActivity(intent)

        }
    }
}