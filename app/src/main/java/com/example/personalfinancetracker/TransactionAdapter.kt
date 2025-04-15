package com.example.personalfinancetracker

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Locale

class TransactionAdapter(
    private val transaction: ArrayList<Transaction>,
    private val onTransactionClick: (Transaction, Int) -> Unit
) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>(){

        class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val titleTextView: TextView = itemView.findViewById(R.id.transactionTitleTextView)
            val amountTextView: TextView = itemView.findViewById(R.id.transactionAmountTextView)
            val categoryTextView: TextView = itemView.findViewById(R.id.transactionCategoryTextView)
            val dateTextView: TextView = itemView.findViewById(R.id.transactionDateTextView)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_item, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transaction[position]
        holder.titleTextView.text = transaction.title
        holder.amountTextView.text = holder.itemView.context.getString(R.string.amount, transaction.amount)
        holder.categoryTextView.text = holder.itemView.context.getString(R.string.category, transaction.category)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        holder.dateTextView.text = holder.itemView.context.getString(R.string.date, dateFormat.format(transaction.date))

        holder.itemView.setOnClickListener {
            onTransactionClick(transaction, position)
        }
    }

    override fun getItemCount() = transaction.size

}