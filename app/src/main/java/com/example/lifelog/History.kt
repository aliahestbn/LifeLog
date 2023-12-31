package com.example.lifelog

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class History : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var databaseReference: DatabaseReference
    private lateinit var userEmailTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        recyclerView = findViewById(R.id.list_entry)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Retrieve user email and set it to the TextView
        userEmailTextView = findViewById(R.id.textView16)
        val user = FirebaseAuth.getInstance().currentUser
        userEmailTextView.text = user?.email

        val userUid = user?.uid ?: ""
        databaseReference = FirebaseDatabase.getInstance().getReference("DiaryEntries").child(userUid)

        fetchDatesFromFirebase()
    }

    private fun fetchDatesFromFirebase() {
        val entriesList = mutableListOf<Triple<String, String, String>>() // Triple of date, content, and ID

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val date = snapshot.child("date").getValue(String::class.java) ?: ""
                    val content = snapshot.child("content").getValue(String::class.java) ?: ""
                    val id = snapshot.key ?: ""

                    entriesList.add(Triple(date, content, id))
                }

                historyAdapter = HistoryAdapter(entriesList) { selectedEntry ->
                    val intent = Intent(this@History, CreateDiary::class.java)
                    intent.putExtra("selectedDate", selectedEntry.first) // Pass the selected date
                    intent.putExtra("selectedContent", selectedEntry.second) // Pass the selected content
                    intent.putExtra("selectedId", selectedEntry.third) // Pass the selected ID
                    startActivity(intent)
                }
                recyclerView.adapter = historyAdapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error
            }
        })
    }


    fun redirectToHomePage(view: View) {
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }
}