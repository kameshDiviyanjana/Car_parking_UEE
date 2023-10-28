package com.example.car_parking

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class displayBooking : AppCompatActivity() {
    private lateinit var recycleBus : RecyclerView
    private lateinit var buslists : ArrayList<Bookpark>
    lateinit var  adapt : Displayeadapter
    private lateinit var dbconnecte : DatabaseReference


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_booking)
        recycleBus =findViewById(R.id.bookingss)
        recycleBus.layoutManager = LinearLayoutManager(this)
        recycleBus.setHasFixedSize(true)

        buslists = arrayListOf()
        adapt = Displayeadapter(buslists)
        displayeBushalt()


    }
    private fun displayeBushalt() {
        dbconnecte = FirebaseDatabase.getInstance().getReference("Reservations")
        dbconnecte.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (busSnapshor in snapshot.children) {
                        val halts = busSnapshor.getValue(Bookpark::class.java)
                        buslists.add(halts!!)
                    }
                    var adp = Displayeadapter(buslists)
                    recycleBus.adapter = adp
                    adp.setonItemClickListener(object : Displayeadapter.onItemClickListener {
                        override fun inItemclick(position: Int) {
                            val intent = Intent(this@displayBooking, mapsActivity::class.java)
                            intent.putExtra("id", buslists[position].name)
                            intent.putExtra("name", buslists[position].parkingName)

                            Toast.makeText(this@displayBooking, "you click item code works", Toast.LENGTH_LONG).show()
                            startActivity(intent)
                        }
                    }
                    )

                   adp.sdsf(object  : Displayeadapter.okkk{
                       override fun paymove(position: Int) {
                           val intent = Intent(this@displayBooking, PaymentScreen::class.java)
                           startActivity(intent)
                       }
                   })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error here
                Toast.makeText(this@displayBooking, "Database error: $error", Toast.LENGTH_LONG).show()
            }
        })
    }

}