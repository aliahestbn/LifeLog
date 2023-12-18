package com.example.lifelog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(
    private val entries: List<Triple<String, String, String>>,
    private val onItemClick: (Triple<String, String, String>) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.EntryViewHolder>() {

    class EntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.entryDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.entry_item, parent, false)
        return EntryViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val (date, _, _) = entries[position] // Extracting only the date

        holder.dateTextView.text = date // Setting the date to the TextView

        holder.itemView.setOnClickListener {
            onItemClick.invoke(entries[position])
        }
    }


    override fun getItemCount(): Int {
        return entries.size
    }
}