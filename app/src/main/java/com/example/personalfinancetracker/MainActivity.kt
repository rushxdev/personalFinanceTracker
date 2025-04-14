package com.example.personalfinancetracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import java.util.ArrayList
import java.util.Date
import java.util.Calendar
import android.app.DatePickerDialog
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var transaction: ArrayList<Transaction>
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var transactionsRecyclerView: RecyclerView
    private lateinit var addTransactionButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        transactionsRecyclerView = findViewById(R.id.transactionsRecyclerView)
        addTransactionButton = findViewById(R.id.addTransactionButton)

        transaction = ArrayList()
        transactionAdapter = TransactionAdapter(transaction)
        transactionsRecyclerView = findViewById(R.id.transactionsRecyclerView)
        transactionsRecyclerView.layoutManager = LinearLayoutManager(this)
        transactionsRecyclerView.adapter = transactionAdapter

        addTransactionButton.setOnClickListener {
            showAddTransactionDialog()
        }
    }

    private fun showAddTransactionDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Transaction")

        val view = LayoutInflater.from(this).inflate(R.layout.dialog_add_transaction, null)
        builder.setView(view)

        val titleEditText = view.findViewById<EditText>(R.id.titleEditText)
        val amountEditText = view.findViewById<EditText>(R.id.amountEditText)
        val categoryEditText = view.findViewById<EditText>(R.id.categoryEditText)
        val dateTextView = view.findViewById<TextView>(R.id.dateTextView)

        var selectedDate = Calendar.getInstance().time

        dateTextView.setOnClickListener {
            showDatePickerDialog(dateTextView) { date ->
                selectedDate = date
            }
        }

        builder.setPositiveButton("Save") { dialog, _ ->
            val title = titleEditText.text.toString()
            val amount = amountEditText.text.toString().toDoubleOrNull() ?: 0.0
            val category = categoryEditText.text.toString()

            if (title.isNotEmpty() && amount != 0.0 && category.isNotEmpty()) {
                val newTransaction = Transaction(title, amount, category, selectedDate)
                addTransaction(newTransaction)
            } else {
                // Handle invalid input
            }
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
       }
        builder.show()

    }
    private fun showDatePickerDialog(dateTextView: TextView, onDateSet: (Date) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
           this,
           { _, year, monthOfYear, dayOfMonth ->
               val selectedCalendar = Calendar.getInstance()
               selectedCalendar.set(year, monthOfYear, dayOfMonth)
               val selectedDate = selectedCalendar.time
               val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate)
               dateTextView.text = formattedDate
               onDateSet(selectedDate)
           },
           year,
           month,
           day
        )
        datePickerDialog.show()
    }

    private fun addTransaction(transaction: Transaction) {
        this.transaction.add(transaction)
        transactionAdapter.notifyItemInserted(this.transaction.size - 1)
        transactionsRecyclerView.scrollToPosition(this.transaction.size - 1)
    }

}

