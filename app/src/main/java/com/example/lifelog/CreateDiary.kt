package com.example.lifelog

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import java.util.UUID

class CreateDiary : AppCompatActivity() {

    private lateinit var dateEditText: EditText
    private lateinit var diaryContentEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creatediary)

        // Initialize the EditTexts
        dateEditText = findViewById(R.id.roundedEditText8)
        diaryContentEditText = findViewById(R.id.roundedEditText7)

        // Retrieve the selected date from the intent
        val selectedDate = intent.getStringExtra("selectedDate")

        // Check if the selected date is not null
        if (selectedDate != null) {
            // Set the selected date to the date EditText
            dateEditText.setText(selectedDate)
        }
    }


    fun redirectToHomePage2(view: View) {
        val diaryContent = diaryContentEditText.text.toString()
        val selectedDate = dateEditText.text.toString()

        if (diaryContent.isNotEmpty()) {
            // Generate a unique ID for the diary entry
            val diaryId = UUID.randomUUID().toString()

            // Reference to the Firebase Database
            val databaseReference = FirebaseDatabase.getInstance().getReference("DiaryEntries")

            // Create a HashMap to store the diary entry data
            val diaryEntry = hashMapOf(
                "date" to selectedDate,
                "content" to diaryContent
            )

            // Save the diary entry to the database with a compound key combining date and unique ID
            databaseReference.child("$selectedDate/$diaryId").setValue(diaryEntry)
                .addOnSuccessListener {
                    Toast.makeText(this, "Diary entry saved!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to save diary entry", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Please enter diary content", Toast.LENGTH_SHORT).show()
        }
    }



    fun redirectToHomePage(view: View) { // Back > HomePage
        val intent = Intent(this, HomePage::class.java);
        startActivity(intent);
    }

    fun redirectToHomePage3(view: View) { //Delete > HomePage
        val intent = Intent(this, HomePage::class.java);
        startActivity(intent);
    }
}