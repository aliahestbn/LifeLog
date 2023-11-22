package com.example.lifelog

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Register : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }
    fun redirectToLogin (view : View){ //Register > Login
        val intent= Intent(this, Login::class.java);
        startActivity(intent);
    }

}