package com.example.car_parking

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate

class listHome : AppCompatActivity() {

    private lateinit var switcher: Switch
    private var nightMode: Boolean = false
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_home)

        val p1: Button = findViewById(R.id.addp1)
        p1.setOnClickListener {
            val intent = Intent(this, mainActivity::class.java)
            startActivity(intent)
        }

        val p2: Button = findViewById(R.id.addp2)
        p2.setOnClickListener {
            val intent = Intent(this, Newazctivety::class.java)
            startActivity(intent)
        }

//        val p3: Button = findViewById(R.id.addp3)
//        p3.setOnClickListener {
//            val intent = Intent(this, MainActivity12::class.java)
//            startActivity(intent)
//        }
//
//        val p4: Button = findViewById(R.id.addp4)
//        p4.setOnClickListener {
//            val intent = Intent(this, MainActivity12::class.java)
//            startActivity(intent)
//        }

        getSupportActionBar()?.hide();
        switcher = findViewById(R.id.switcher);

        //We use sharedPreferences to save mode if exit the app and go back again

        sharedPreferences = getSharedPreferences("MODE", MODE_PRIVATE)
        nightMode = sharedPreferences.getBoolean("night", false) // false is the default mode

        if (nightMode) {
            switcher.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        switcher.setOnClickListener {
            if (nightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor = sharedPreferences.edit()
                editor.putBoolean("night", false)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor = sharedPreferences.edit()
                editor.putBoolean("night", true)
            }
            editor.apply()
        }

    }
}