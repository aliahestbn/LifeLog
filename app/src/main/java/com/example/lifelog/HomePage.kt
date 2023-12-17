package com.example.lifelog

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomePage : AppCompatActivity() {

    private var selectedDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val calendarView: CalendarView = findViewById(R.id.calendarView)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$year-${month + 1}-$dayOfMonth"
        }
    }

    fun redirectToCreateDiary(view: View) {
        if (selectedDate != null) {
            Toast.makeText(this, "Selected date for Diary: $selectedDate", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, CreateDiary::class.java)
            intent.putExtra("selectedDate", selectedDate)
            startActivity(intent)
        } else {
            val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            Toast.makeText(this, "No date selected. Using current date: $currentDate", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, CreateDiary::class.java)
            intent.putExtra("selectedDate", currentDate)
            startActivity(intent)
        }
    }

    fun redirectToEditProfile(view: View) {
        val intent = Intent(this, EditProfile::class.java)
        startActivity(intent)
    }

    fun redirectToHistory(view: View) {
        val intent = Intent(this, History::class.java)
        startActivity(intent)
    }

    fun redirectToMain(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
