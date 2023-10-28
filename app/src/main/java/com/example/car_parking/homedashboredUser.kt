package com.example.car_parking

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

class homedashboredUser : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homedashbored_user)
       var mapsvs = findViewById<ImageView>(R.id.mapview)
        var bookingim =findViewById<ImageView>(R.id.bokinggo)
        var btnbook = findViewById<Button>(R.id.reciverplace)
        var find = findViewById<EditText>(R.id.findg)
        var mypoints = findViewById<Button>(R.id.btnviewpoints)

        find.setOnClickListener {
            val mapsAct = Intent(this, mapsActivity::class.java)

            startActivity(mapsAct)
        }
        btnbook.setOnClickListener {
            val os = Intent(this, VehicleBooking::class.java)

            startActivity(os)
        }
        mapsvs.setOnClickListener {
            val mapsAct = Intent(this, mapsActivity::class.java)

            startActivity(mapsAct)

        }

        mypoints.setOnClickListener {
            val points = Intent(this, MyPoints::class.java)

            startActivity(points)

        }

        bookingim.setOnClickListener {
            val bookinAct = Intent(this, displayBooking::class.java)

            startActivity(bookinAct)
        }
    }
}