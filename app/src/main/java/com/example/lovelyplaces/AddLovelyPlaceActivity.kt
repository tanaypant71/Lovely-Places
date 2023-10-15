package com.example.lovelyplaces

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddLovelyPlaceActivity : AppCompatActivity() , View.OnClickListener{

    private var cal = Calendar.getInstance()
    private lateinit var dateSetListener: OnDateSetListener
    private var et_date: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lovely_place)
        et_date = findViewById(R.id.et_date)
        var toolbar_add_place: Toolbar = findViewById(R.id.toolbar_add_place)
        setSupportActionBar(toolbar_add_place)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_add_place.setNavigationOnClickListener{
            onBackPressed()
        }

        dateSetListener = OnDateSetListener {
                view, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR,year)
            cal.set(Calendar.MONTH,month)
            cal.set(Calendar.DAY_OF_MONTH,dayOfMonth)
        }
        et_date?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        //here in this onClick method we are checking if the user clicks the date option on the
        //screen and we pass a lamda func to execute code to select the date which the user wants
        when(v!!.id)
        {
            R.id.et_date ->{
                DatePickerDialog(
                    this@AddLovelyPlaceActivity,
                    dateSetListener,cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
                    updateDateInView()
            }
        }
    }

    private fun updateDateInView()
    {
        val myFormat = "dd.MM.yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())   // A date format
        et_date?.setText(sdf.format(cal.time).toString())    // A selected date using format which we have used is set to the UI.
    }
}