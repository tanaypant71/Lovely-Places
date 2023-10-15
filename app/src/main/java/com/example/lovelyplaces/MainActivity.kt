package com.example.lovelyplaces

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addLovelyPlaces: FloatingActionButton = findViewById(R.id.addLovelyPlaces)
        addLovelyPlaces.setOnClickListener {
            val intent = Intent(this,AddLovelyPlaceActivity::class.java)
            startActivity(intent)
        }
    }
}