package com.example.todolist

import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class InputAccActivity : AppCompatActivity() {

    private lateinit var etCompany: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSimpan: Button
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_acc)

        dbHelper = DatabaseHelper(this)

        etCompany = findViewById(R.id.etCompany)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnSimpan = findViewById(R.id.btnSimpan)

        btnSimpan.setOnClickListener {
            val company = etCompany.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (company.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                val result = dbHelper.insertAccount(company, email, password)
                if (result != -1L) {
                    Toast.makeText(this, "Account saved!", Toast.LENGTH_SHORT).show()
                    finish() // Close this activity and return to AccActivity
                } else {
                    Toast.makeText(this, "Error saving account", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
