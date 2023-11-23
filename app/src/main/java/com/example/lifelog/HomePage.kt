package com.example.lifelog

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HomePage : AppCompatActivity() {

    // Variable to store the selected date
    private var selectedDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val calendarView: CalendarView = findViewById(R.id.calendarView)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // Store the selected date
            selectedDate = "$year-${month + 1}-$dayOfMonth"
        }
    }

    fun redirectToCreateDiary(view: View) {
        // Check if a date is selected
        if (selectedDate != null) {
            // Display a Toast message with the selected date
            Toast.makeText(this, "Selected date for Diary: $selectedDate", Toast.LENGTH_SHORT).show()

            // Redirect to CreateDiary with the selected date as an extra
            val intent = Intent(this, CreateDiary::class.java)
            intent.putExtra("selectedDate", selectedDate)
            startActivity(intent)
        } else {
            // Display a message if no date is selected
            Toast.makeText(this, "Please select a date first", Toast.LENGTH_SHORT).show()
        }
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

