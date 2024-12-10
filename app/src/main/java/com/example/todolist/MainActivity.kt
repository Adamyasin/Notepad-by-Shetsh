package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_main)

        val btnAcc = findViewById<Button>(R.id.btn_acc)

        btnAcc.setOnClickListener {
            val intent = Intent(this, AccActivity::class.java)
            startActivity(intent)
        }

        val btnLog = findViewById<Button>(R.id.btn_log)

        btnLog.setOnClickListener {
            val intent = Intent(this,LogActivity::class.java )
            startActivity(intent)
        }

        val btnList = findViewById<Button>(R.id.btn_list)

        btnList.setOnClickListener {
            val intent = Intent(this,ListActivity::class.java )
            startActivity(intent)
        }


    }
}