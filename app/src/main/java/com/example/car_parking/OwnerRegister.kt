package com.example.car_parking

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class OwnerRegister : Fragment() {

    lateinit var dbcon : DatabaseReference
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val g = inflater.inflate(R.layout.ownerrefrgmaent,container,false)
        var username = g.findViewById<EditText>(R.id.textnameowne)
        var useremail = g.findViewById<EditText>(R.id.textemailowner)
        var userpasswor = g.findViewById<EditText>(R.id.txtpasswordowner)
        var sigup = g.findViewById<Button>(R.id.regidterbtn)
        var backbtn = g.findViewById<Button>(R.id.back)

        backbtn.setOnClickListener {
            val move = Intent(this.context, loginAct::class.java)
            startActivity(move)
        }
        sigup.setOnClickListener {

            dbcon = FirebaseDatabase.getInstance().getReference("Usersregister")

            val Username =username.text.toString()
            val Passwored =userpasswor.text.toString()
            val email = useremail.text.toString()


            if(Username.isNotEmpty() && Passwored.isNotEmpty() && email.isNotEmpty()){

                 val type  = "o"
                val adduser =   userdata(Username,Passwored,email,type)

                dbcon.child(Username).setValue(adduser).addOnSuccessListener{

                    val move = Intent(this.context, loginAct::class.java)
                    startActivity(move)
                }.addOnFailureListener {

                    Toast.makeText(this.context,"Sign up failed.Please try Again!", Toast.LENGTH_LONG).show()
                }

            }else{

                Toast.makeText(this.context,"Enter all the fields", Toast.LENGTH_LONG).show()
            }
        }
        return g
    }
}