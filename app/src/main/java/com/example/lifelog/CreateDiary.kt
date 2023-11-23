package com.example.lifelog

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

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
        // Retrieve the diary content from the EditText
        val diaryContent = diaryContentEditText.text.toString()

        // Check if the diary content is not empty
        if (diaryContent.isNotEmpty()) {
            // TODO: Save the diary entry to (e.g., database, file)
            // For now, just display a Toast message
            Toast.makeText(this, "Diary entry saved!", Toast.LENGTH_SHORT).show()
        } else {
            // Display a message if the diary content is empty
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
