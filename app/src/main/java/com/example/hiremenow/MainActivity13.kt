package com.example.hiremenow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class MainActivity13 : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var usersArrayList: ArrayList<Users>
    private lateinit var database:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main13)


        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this )

        usersArrayList = arrayListOf()

        database = FirebaseDatabase.getInstance().getReference("Customers")
        database.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(dataSnapShot in snapshot.children){
                        val user = dataSnapShot.getValue(Users::class.java)
                        if(!usersArrayList.contains(user)){
                            usersArrayList.add(user!!)

                        }
                    }
                    val mAdaptor= MyAdapter(usersArrayList)
                    recyclerView.adapter = mAdaptor

                    mAdaptor.setOnItemClickListener(object:MyAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                           val intent = Intent(this@MainActivity13,MainActivity2::class.java)
                            //put extras
                            intent.putExtra("customerId",usersArrayList[position].customerId)
                            intent.putExtra("customerName",usersArrayList[position].customerName)
                            intent.putExtra("customerEmail",usersArrayList[position].customerEmail)
                            intent.putExtra("customerPhone",usersArrayList[position].customerPhone)
                            intent.putExtra("customerSubject",usersArrayList[position].customerSubject)
                            intent.putExtra("customerMessage",usersArrayList[position].customerMessage)
                            startActivity(intent)
                        }

                    })




                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity13,error.toString(), Toast.LENGTH_SHORT).show()
            }
        })


    }

}