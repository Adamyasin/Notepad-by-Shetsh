import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.DatabaseHelper
import com.example.todolist.R
import com.google.android.material.button.MaterialButton

class AccountAdapter(
    private var cursor: Cursor,
    private val onDeleteClick: (String) -> Unit // Listener untuk aksi hapus
) : RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {

    // Pastikan nama kolom sesuai dengan yang ada di database
    private val COLUMN_ID = DatabaseHelper.COLUMN_ID
    private val COLUMN_COMPANY = DatabaseHelper.COLUMN_COMPANY
    private val COLUMN_EMAIL = DatabaseHelper.COLUMN_EMAIL
    private val COLUMN_PASSWORD = DatabaseHelper.COLUMN_PASSWORD

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_acc, parent, false)
        return AccountViewHolder(view)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        cursor.moveToPosition(position)

        // Pastikan indeks kolom valid
        val idIndex = cursor.getColumnIndex(COLUMN_ID)
        val companyIndex = cursor.getColumnIndex(COLUMN_COMPANY)
        val emailIndex = cursor.getColumnIndex(COLUMN_EMAIL)
        val passwordIndex = cursor.getColumnIndex(COLUMN_PASSWORD)

        if (idIndex != -1 && companyIndex != -1 && emailIndex != -1 && passwordIndex != -1) {
            val id = cursor.getString(idIndex)
            val company = cursor.getString(companyIndex)
            val email = cursor.getString(emailIndex)
            val password = cursor.getString(passwordIndex)

            holder.tvCompany.text = company
            holder.tvEmail.text = "Email: $email"
            holder.tvPassword.text = "Password: $password"

            // Set listener untuk tombol hapus
            holder.btnDelete.setOnClickListener {
                onDeleteClick(id) // Kirim ID ke listener
            }
        } else {
            // Jika kolom tidak ditemukan, tampilkan pesan error atau kosongkan
            holder.tvCompany.text = "N/A"
            holder.tvEmail.text = "Email: N/A"
            holder.tvPassword.text = "Password: N/A"
        }
    }

    override fun getItemCount(): Int {
        return cursor.count
    }

    fun updateCursor(newCursor: Cursor) {
        cursor.close() // Tutup cursor lama
        cursor = newCursor // Set cursor baru
        notifyDataSetChanged() // Perbarui tampilan
    }

    class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCompany: TextView = itemView.findViewById(R.id.tvCompany)
        val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
        val tvPassword: TextView = itemView.findViewById(R.id.tvPassword)
        val btnDelete: MaterialButton = itemView.findViewById(R.id.btnDelete) // Tambahkan tombol delete
    }
}
