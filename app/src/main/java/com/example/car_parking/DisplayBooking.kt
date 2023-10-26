package com.example.car_parking

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    lateinit var  adapt : displayeadapter
    private lateinit var dbconnecte : DatabaseReference
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_booking)
        recycleBus =findViewById(R.id.bookingss)
        recycleBus.layoutManager = LinearLayoutManager(this)
        recycleBus.setHasFixedSize(true)

        buslists = arrayListOf()
        adapt = displayeadapter(buslists)
        displayeBushalt()
    }
    private fun displayeBushalt() {
        dbconnecte = FirebaseDatabase.getInstance().getReference("RecervetionPlace")
        dbconnecte.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){

                    for (busSnapshor in snapshot.children){
                        val halts = busSnapshor.getValue(Bookpark::class.java)
                        buslists.add(halts!!)
                    }
                    var adp = displayeadapter(buslists)
                    recycleBus.adapter =adp
                    adp.setonItemClickListener(object : displayeadapter.onItemClickListener {
                        override fun inItemckick(position: Int) {
                            val intent = Intent(this@displayBooking, mapsActivity::class.java)
                            intent.putExtra("id",buslists[position].names)
                            intent.putExtra("name",buslists[position].pakingplaces)

                            startActivity(intent)
                            Toast.makeText(this@displayBooking,"you click item nrwfgvhvhvh code works", Toast.LENGTH_LONG).show()

                        }

                    })
                    /*   adapt = Myadapter(buslist)
                       displayeBushalt()
                       adapt.onItemclick ={
                           val intent =Intent(this@MainActivity,BusTickeclculate::class.java)
                           intent.putExtra("ticket",it)

                           startActivity(intent)
                       }*/
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }




        })

    }
}