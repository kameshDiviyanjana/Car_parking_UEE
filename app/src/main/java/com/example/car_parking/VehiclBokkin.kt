package com.example.car_parking

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class vehiclBokkin : AppCompatActivity() {
    private lateinit var dbconection: DatabaseReference

    @SuppressLint("WrongViewCast", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicl_bokkin)


        val book = findViewById<Button>(R.id.updatesbtn)
        val cnacsel = findViewById<Button>(R.id.deletebtns)
        val name = findViewById<EditText>(R.id.nma)
        val vehicnum = findViewById<EditText>(R.id.vehicnum)
        val phonenum = findViewById<EditText>(R.id.phonenum)
        val pakingplace = findViewById<EditText>(R.id.parking)
        val time = findViewById<EditText>(R.id.timewhre)
        val slot = findViewById<EditText>(R.id.slot)

        book.setOnClickListener {


            val names = name.text.toString()
            val vehicnumer = vehicnum.text.toString()
            val phonenumber =phonenum.text.toString()
            val pakingplaces = pakingplace.text.toString()
            val timeresive = time.text.toString()
            val slotbook = slot.text.toString()


            dbconection = FirebaseDatabase.getInstance().getReference("RecervetionPlace")
            val addtime = Bookpark(names,vehicnumer,phonenumber,pakingplaces,timeresive,slotbook)

            dbconection.child(names).setValue(addtime).addOnSuccessListener {
                name.text.clear()
                vehicnum.text.clear()
                phonenum.text.clear()
                pakingplace.text.clear()
                time.text.clear()
                slot.text.clear()
                Toast.makeText(this,"Add sucefull",Toast.LENGTH_LONG).show()

              val ye =Intent(this, displayBooking::class.java)
                startActivity(ye)

            }.addOnFailureListener {
                Toast.makeText(this,"DB connection fails",Toast.LENGTH_LONG).show()
            }

        }



        cnacsel.setOnClickListener {

             name.text.clear()
            vehicnum.text.clear()
            phonenum.text.clear()
            pakingplace.text.clear()
            time.text.clear()
            slot.text.clear()


        }

    }
}