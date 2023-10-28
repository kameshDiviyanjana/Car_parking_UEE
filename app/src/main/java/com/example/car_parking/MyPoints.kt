package com.example.car_parking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MyPoints : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_points)

        var btnDash = findViewById<Button>(R.id.btnloyaltygohome)

        btnDash.setOnClickListener {
            val home = Intent(this, homedashboredUser::class.java)

            startActivity(home)

        }
    }
}