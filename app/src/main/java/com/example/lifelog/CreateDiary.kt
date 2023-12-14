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

        val selectedDate = intent.getStringExtra("selectedDate")
        val selectedContent = intent.getStringExtra("selectedContent")
        val selectedId = intent.getStringExtra("selectedId")

        dateEditText.setText(selectedDate)
        diaryContentEditText.setText(selectedContent)

        if (!selectedId.isNullOrEmpty()) {
            findViewById<View>(R.id.imageButton11).setOnClickListener {
                val diaryContent = diaryContentEditText.text.toString()
                val newSelectedDate = dateEditText.text.toString()
                val userUid = getCurrentUserUid()

                if (userUid != null && diaryContent.isNotEmpty() && newSelectedDate.isNotEmpty()) {
                    val databaseReference = FirebaseDatabase.getInstance()
                        .getReference("DiaryEntries")
                        .child(userUid)
                        .child(selectedId)

                    val diaryEntry = hashMapOf(
                        "date" to newSelectedDate,
                        "content" to diaryContent
                    )

                    databaseReference.updateChildren(diaryEntry as MutableMap<String, Any>)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Diary entry updated!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, HomePage::class.java)
                            startActivity(intent)
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Failed to update diary entry", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(
                        this,
                        "Please enter both date and diary content",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            findViewById<View>(R.id.imageButton12).setOnClickListener {
                val userUid = getCurrentUserUid()

                if (userUid != null && !selectedId.isNullOrEmpty()) {
                    val databaseReference = FirebaseDatabase.getInstance()
                        .getReference("DiaryEntries")
                        .child(userUid)
                        .child(selectedId)

                    databaseReference.removeValue()
                        .addOnSuccessListener {
                            Toast.makeText(this, "Diary entry deleted!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, HomePage::class.java)
                            startActivity(intent)
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Failed to delete diary entry", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "No entry to delete", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            findViewById<View>(R.id.imageButton11).setOnClickListener {
                redirectToHomePage2(it)
            }
        }
    }


    private fun getCurrentUserUid(): String? {
        return firebaseAuth.currentUser?.uid
    }

    fun redirectToHomePage2(view: View) {
        val diaryContent = diaryContentEditText.text.toString()
        val selectedDate = dateEditText.text.toString()

        val userUid = getCurrentUserUid()

        if (userUid != null && diaryContent.isNotEmpty() && selectedDate.isNotEmpty()) {
            val diaryId = UUID.randomUUID().toString()

            val databaseReference =
                FirebaseDatabase.getInstance().getReference("DiaryEntries").child(userUid)

            val diaryEntry = hashMapOf(
                "date" to selectedDate,
                "content" to diaryContent
            )

            // Save the diary entry under a user's section with a unique ID
            databaseReference.child(diaryId).setValue(diaryEntry)
                .addOnSuccessListener {
                    Toast.makeText(this, "Diary entry saved!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to save diary entry", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Please enter both date and diary content", Toast.LENGTH_SHORT).show()
        }
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
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