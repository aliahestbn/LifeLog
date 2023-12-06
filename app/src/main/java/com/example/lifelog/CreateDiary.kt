package com.example.lifelog

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.UUID

class CreateDiary : AppCompatActivity() {

    private lateinit var dateEditText: EditText
    private lateinit var diaryContentEditText: EditText
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creatediary)

        dateEditText = findViewById(R.id.roundedEditText8)
        diaryContentEditText = findViewById(R.id.roundedEditText7)

        firebaseAuth = FirebaseAuth.getInstance()

        // Retrieve the selected date from the intent
        val selectedDate = intent.getStringExtra("selectedDate")

        // Log the selected date for debugging
        Toast.makeText(this, "Selected Date from Intent: $selectedDate", Toast.LENGTH_SHORT).show()
        Log.d("CreateDiary", "Selected Date from Intent: $selectedDate")

        // Check if the selected date is not null
        if (selectedDate != null) {
            // Set the selected date to the date EditText
            dateEditText.setText(selectedDate)
        }
    }

    private fun getCurrentUserUid(): String? {
        return firebaseAuth.currentUser?.uid
    }

    fun redirectToHomePage2(view: View) {
        val diaryContent = diaryContentEditText.text.toString()
        val selectedDate = dateEditText.text.toString()

        val userUid = getCurrentUserUid()

        if (userUid != null && diaryContent.isNotEmpty()) {
            val diaryId = UUID.randomUUID().toString()

            val databaseReference =
                FirebaseDatabase.getInstance().getReference("DiaryEntries").child(userUid)

            val diaryEntry = hashMapOf(
                "date" to selectedDate,
                "content" to diaryContent
            )

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

    fun redirectToHomePage(view: View) {
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }

    fun redirectToHomePage3(view: View) {
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }
}
