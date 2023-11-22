package com.example.lifelog

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class EditProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editprofile)
    }
    fun redirectToHomePage(view: View) { // Back > HomePage
        val intent = Intent(this, HomePage::class.java);
        startActivity(intent);
    }
    fun redirectToHomePage2(view: View) { //Save Profile > HomePage
        val intent = Intent(this, HomePage::class.java);
        startActivity(intent);
    }
}