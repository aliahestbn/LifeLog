package com.example.lifelog

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Login : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
    fun redirectToHomepage(view: View) { //Login > HomePage
        val intent = Intent(this, HomePage::class.java);
        startActivity(intent);
    }

}