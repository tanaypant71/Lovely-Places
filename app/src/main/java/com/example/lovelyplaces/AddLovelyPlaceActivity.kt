package com.example.lovelyplaces

import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddLovelyPlaceActivity : AppCompatActivity() , View.OnClickListener{

    private var cal = Calendar.getInstance()
    private lateinit var dateSetListener: OnDateSetListener
    private var et_date: EditText? = null
    private var tv_add_image: EditText? = null

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
        tv_add_image?.setOnClickListener(this)
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
            R.id.tv_add_image -> {
                val pictureDialog = AlertDialog.Builder(this)
                pictureDialog.setTitle("Select Action")
                val pictureDialogItems =
                    arrayOf("Select photo from gallery", "Capture photo from camera")
                pictureDialog.setItems(
                    pictureDialogItems
                ) { dialog, which ->
                    when (which) {
                        // Here we have create the methods for image selection from GALLERY
                        0 -> choosePhotoFromGallery()
                        1 -> takePhotoFromCamera()
                    }
                }
                pictureDialog.show()
            }
        }
    }

    private fun choosePhotoFromGallery(){
        //here we are using the DEXTER third party library for asking permissions for accessing storage and camera ,
        // all the code is available on the DEXTER site on the github and I have made some minor changes according to the tutorial
        //also we are asking for multiple permissions in the app so we are using the "MultiplePermissionsListener"
        Dexter.withActivity(this).withPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).withListener(object :  MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                // Here after all the permission are granted launch the gallery to select and image.
                if (report.areAllPermissionsGranted()){

                }
            }
            override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest> , token: PermissionToken) {
                showRationalDialogForPermissions()
            }
        }).onSameThread().check()
    }

    private fun takePhotoFromCamera(){

    }

    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(this)
            .setMessage("It Looks like you have turned off permissions required for this feature. " +
                    "It can be enabled under Application Settings")
            .setPositiveButton(
                "GO TO SETTINGS"
            ) { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun updateDateInView()
    {
        val myFormat = "dd.MM.yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())   // A date format
        et_date?.setText(sdf.format(cal.time).toString())    // A selected date using format which we have used is set to the UI.
    }
}