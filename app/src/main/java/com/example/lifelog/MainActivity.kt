package com.example.lifelog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.database.DatabaseReference

data class DatabaseClass(   var EmpName : String,
                            var EmpPosition : String,
                            var EmpDepartment : String)

class MainActivity : AppCompatActivity() {
    lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun redirectionfun(view: View) { //Main > Login
        val intent = Intent(this, Login::class.java);
        startActivity(intent);
    }
    fun redirectionfun2(view: View) { //Main > Login
        val intent = Intent(this, Register::class.java);
        startActivity(intent);
    }

}