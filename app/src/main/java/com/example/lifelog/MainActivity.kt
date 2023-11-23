package com.example.lifelog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

data class DatabaseClass(   var EmpName : String,
                            var EmpPosition : String,
                            var EmpDepartment : String)

class MainActivity : AppCompatActivity() {
    lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //create
        databaseReference = FirebaseDatabase.getInstance().getReference("DatabaseName")
        var databaseClass = DatabaseClass("Park Jihoon", "SSE", "Somewhere")

        var dataKey = databaseReference.push().getKey()
        databaseReference.child("uniqueid").setValue(databaseClass).addOnSuccessListener {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        }

        //update
        var empUpdate = mapOf<String, String>("empName" to "Hwang Minhyun")
        databaseReference.child("uniqueid").child("sampleOne").updateChildren(empUpdate).addOnSuccessListener {
            Toast.makeText(this, "Update", Toast.LENGTH_SHORT).show()
        }

        //remove
        databaseReference.child("uniqueid").child("sampleOne").removeValue().addOnSuccessListener {
            Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show()
        }
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
