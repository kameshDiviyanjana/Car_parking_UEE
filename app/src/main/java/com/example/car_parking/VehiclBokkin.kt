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

    @SuppressLint("WrongViewCast", "MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicl_bokkin)


        val book = findViewById<Button>(R.id.updatesbtn)
        val cnacsel = findViewById<Button>(R.id.deletebtns)
        val name = findViewById<EditText>(R.id.nma)
        val vehicnum = findViewById<EditText>(R.id.vehicnum)
        val phonenum = findViewById<EditText>(R.id.phonenum)
        val pakingplace = findViewById<EditText>(R.id.parking)
        val date = findViewById<EditText>(R.id.date)
        val starttime = findViewById<EditText>(R.id.start_time)
        val endtime = findViewById<EditText>(R.id.end_time)
        val slot = findViewById<EditText>(R.id.slot)

        book.setOnClickListener {


            val Name = name.text.toString()
            val vehicnumer = vehicnum.text.toString()
            val phonenumber =phonenum.text.toString()
            val Date = date.text.toString()
            val Starttime = starttime.text.toString()
            val Endtime = endtime.text.toString()
            val pakingplaces = pakingplace.text.toString()
            val slotbook = slot.text.toString()


            dbconection = FirebaseDatabase.getInstance().getReference("RecervetionPlace")
            val addtime = Bookpark(Name,vehicnumer,phonenumber,pakingplaces,Date,slotbook,Starttime,Endtime)

            dbconection.child(Name).setValue(addtime).addOnSuccessListener {
                name.text.clear()
                phonenum.text.clear()
                vehicnum.text.clear()
                date.text.clear()
                starttime.text.clear()
                endtime.text.clear()
                pakingplace.text.clear()
                slot.text.clear()



                Toast.makeText(this,"Slot booked successfully",Toast.LENGTH_LONG).show()

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
            date.text.clear()
            starttime.text.clear()
            endtime.text.clear()
            pakingplace.text.clear()
            slot.text.clear()


        }

    }
}