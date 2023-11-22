package com.example.lifelog

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class HomePage : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
    }
    fun redirectToCreateDiary(view: View) { //HomePage > CreateDiary
        val intent = Intent(this, CreateDiary::class.java);
        startActivity(intent);
    }
    fun redirectToEditProfile(view: View) { //HomePage > EditProfile
        val intent = Intent(this, EditProfile::class.java);
        startActivity(intent);
    }
    fun redirectToHistory(view: View) { //HomePage > History
        val intent = Intent(this, History::class.java);
        startActivity(intent);
    }
    fun redirectToMain(view: View) { //HomePage > Logout
        val intent = Intent(this, MainActivity::class.java);
        startActivity(intent);
    }

}