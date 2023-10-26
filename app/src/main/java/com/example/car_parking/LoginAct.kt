package com.example.car_parking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class loginAct : AppCompatActivity() {
    private lateinit var dbconnecte : DatabaseReference
    lateinit var singup  : Button
    lateinit var reist : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        singup = findViewById(R.id.singupbtn)
        var username = findViewById<EditText>(R.id.editTextTextPersonName)
        var passwork = findViewById<EditText>(R.id.editTextTextPassword)
        reist = findViewById(R.id.reister)
        reist.setOnClickListener {
            val os = Intent(this, RegisterUsercar::class.java)

            startActivity(os)
        }
        singup.setOnClickListener {

            dbconnecte = FirebaseDatabase.getInstance().getReference("Usersregister")
            var  userName = username.text.toString()
            var userpassword = passwork.text.toString()
            dbconnecte.child(userName).get().addOnSuccessListener {

                if(it.exists()){
                    val auser = it.child("username").value
                    val email= it.child("email").value
                    val passw =it.child("passwored").value
                    val disname = it.child("displayename").value
                     val type = it.child("typs").value

                    val  p = auser.toString()
                    if ((userName ==auser) && (userpassword == passw) && (type == "u")) {

                        val os = Intent(this, homedashboredUser::class.java)

                        startActivity(os)
                    }else if((userName ==auser) && (userpassword == passw) && (type == "o")){
                        val os = Intent(this, listHome::class.java)

                        startActivity(os)

                    } else{
                        Toast.makeText(this,"Fill all Fields", Toast.LENGTH_LONG).show()
                    }

                }else{

                    Toast.makeText(this,"Incorrect username or Password", Toast.LENGTH_LONG).show()
                }
            }

        }


    }
}