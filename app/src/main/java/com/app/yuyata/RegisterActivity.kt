package com.app.yuyata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }


    //Register button click
    fun onRegisterUserButtonClick(view: View){
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}