package com.example.todolist

import AccountAdapter
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AccActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AccountAdapter
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var btnAdd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acc)

        // Inisialisasi database dan UI
        dbHelper = DatabaseHelper(this)
        recyclerView = findViewById(R.id.recyclerView1)
        btnAdd = findViewById(R.id.btnAdd)

        // Atur RecyclerView dengan LinearLayoutManager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inisialisasi adapter dengan cursor dan aksi hapus
        adapter = AccountAdapter(dbHelper.getAllAccounts()) { accountId ->
            showDeleteConfirmationDialog(accountId)
        }
        recyclerView.adapter = adapter

        // Aksi tombol "Add" untuk membuka aktivitas InputAccActivity
        btnAdd.setOnClickListener {
            startActivity(Intent(this, InputAccActivity::class.java))
        }
    }

    // Fungsi untuk menampilkan dialog konfirmasi penghapusan
    private fun showDeleteConfirmationDialog(accountId: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Konfirmasi Hapus")
            .setMessage("Yakin ingin menghapus akun ini?")
            .setIcon(R.drawable.imgalert) // Tambahkan ikon alert
            .setPositiveButton("Iya") { _, _ ->
                deleteAccount(accountId)
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss() // Tutup dialog tanpa melakukan apapun
            }
            .create()
            .show()
    }

    // Fungsi untuk menghapus akun
    private fun deleteAccount(accountId: String) {
        val success = dbHelper.deleteAccount(accountId)
        if (success) {
            Toast.makeText(this, "Account deleted successfully", Toast.LENGTH_SHORT).show()
            refreshAccounts() // Perbarui data di RecyclerView
        } else {
            Toast.makeText(this, "Failed to delete account", Toast.LENGTH_SHORT).show()
        }
    }

    // Fungsi untuk memperbarui data di RecyclerView
    private fun refreshAccounts() {
        val updatedCursor = dbHelper.getAllAccounts()
        adapter.updateCursor(updatedCursor) // Perbarui adapter dengan cursor baru
    }

    override fun onResume() {
        super.onResume()
        refreshAccounts() // Segarkan data saat aktivitas kembali aktif
    }
}
