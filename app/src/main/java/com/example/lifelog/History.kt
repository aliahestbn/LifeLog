package com.example.lifelog

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class History : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
    }
    fun redirectToHomePage(view: View) { // Back > HomePage
        val intent = Intent(this, HomePage::class.java);
        startActivity(intent);
    }
}