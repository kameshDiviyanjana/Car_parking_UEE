package com.example.car_parking

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterUsercar : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var dbcon : DatabaseReference
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_usercar)
            val user = findViewById<ImageView>(R.id.userim)
        val owner = findViewById<ImageView>(R.id.ownerim)

        var fragmentManager =supportFragmentManager
        val frgmentTransaction =fragmentManager.beginTransaction()
        frgmentTransaction.replace(R.id.myfragments,UserRegister()).commit()

      user.setOnClickListener {

          replaceFragmet(UserRegister())
      }

        owner.setOnClickListener {
    replaceFragmet(OwnerRegister())

        }

    }

    private fun replaceFragmet(userRegister: Fragment) {

        var fragmentManager =supportFragmentManager
        val frgmentTransaction =fragmentManager.beginTransaction()
        frgmentTransaction.replace(R.id.myfragments,userRegister).commit()
    }
}