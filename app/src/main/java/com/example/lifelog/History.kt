package com.example.lifelog

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class History : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        recyclerView = findViewById(R.id.list_entry)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val userUid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        databaseReference = FirebaseDatabase.getInstance().getReference("DiaryEntries").child(userUid)

        fetchDatesFromFirebase()
    }

    private fun fetchDatesFromFirebase() {
        val datesList = mutableListOf<String>()

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val date = snapshot.key
                    date?.let { datesList.add(it) }
                }

                historyAdapter = HistoryAdapter(datesList)
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
