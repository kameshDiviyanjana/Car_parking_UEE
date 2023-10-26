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

class UserRegister : Fragment() {

    lateinit var dbcon : DatabaseReference
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val o = inflater.inflate(R.layout.userregisterfragmain,container,false)


        var username = o.findViewById<EditText>(R.id.textnameowne)
        var useremail = o.findViewById<EditText>(R.id.textemailowner)
        var userpasswor = o.findViewById<EditText>(R.id.txtpasswordowner)
        var sigup = o.findViewById<Button>(R.id.regidterbtn)
        var backbtn = o.findViewById<Button>(R.id.back)


        backbtn.setOnClickListener {
            val move =Intent(this.context, loginAct::class.java)
            startActivity(move)
        }
        sigup.setOnClickListener {

            dbcon = FirebaseDatabase.getInstance().getReference("Usersregister")

            val Username =username.text.toString()
            val Passwored =userpasswor.text.toString()
            val email = useremail.text.toString()
            val typs = "u"

            if(Username.isNotEmpty() && Passwored.isNotEmpty() && email.isNotEmpty()){

                val adduser =   userdata(Username,Passwored,email,typs)

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

        return o
    }
}