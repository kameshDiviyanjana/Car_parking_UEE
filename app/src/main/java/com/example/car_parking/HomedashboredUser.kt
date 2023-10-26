package com.example.car_parking

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class homedashboredUser : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homedashbored_user)
       var mapsvs = findViewById<ImageView>(R.id.mapview)
        var bookingim =findViewById<ImageView>(R.id.bokinggo)
        var btnbook = findViewById<Button>(R.id.reciverplace)

        btnbook.setOnClickListener {
            val os = Intent(this, vehiclBokkin::class.java)

            startActivity(os)
        }
        mapsvs.setOnClickListener {
            val mapsAct = Intent(this, mapsActivity::class.java)

            startActivity(mapsAct)

        }

        bookingim.setOnClickListener {
            val bookinAct = Intent(this, displayBooking::class.java)

            startActivity(bookinAct)
        }
    }
}