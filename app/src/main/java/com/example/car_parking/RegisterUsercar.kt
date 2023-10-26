package com.example.car_parking

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference

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
        frgmentTransaction.replace(R.id.myfragments, UserRegister()).commit()

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