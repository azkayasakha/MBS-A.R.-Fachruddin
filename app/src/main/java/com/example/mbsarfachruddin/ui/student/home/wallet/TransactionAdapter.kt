package com.example.mbsarfachruddin.ui.student.home.wallet

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.databinding.ItemStudentWalletBinding
import dev.androidbroadcast.vbpd.viewBinding
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import androidx.core.graphics.toColorInt
import com.example.mbsarfachruddin.model.remote.student.transaction.History
import java.text.DecimalFormat
import java.time.LocalDateTime
import kotlin.math.abs

class TransactionAdapter(private val listTransaction: List<com.example.mbsarfachruddin.model.remote.student.transaction.History>) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {
    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemStudentWalletBinding by viewBinding(ItemStudentWalletBinding::bind)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionAdapter.TransactionViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_student_wallet, parent, false)
        return TransactionViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TransactionAdapter.TransactionViewHolder, position: Int) {
        val transaction = listTransaction[position]

        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val dateTime = LocalDateTime.parse(transaction.createdAt, inputFormatter)
        val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yy")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val formattedDate = dateTime.format(dateFormatter)
        val formattedTime = dateTime.format(timeFormatter)

        with(holder.binding) {
            tvDate.text = formattedDate
            tvTime.text = formattedTime
            tvAmount.text = formatCurrency(transaction.total)
            if (transaction.total < 0) {
                tvAmount.setTextColor("#FF0000".toColorInt())
            }
            tvBalance.text = formatCurrency(transaction.balance)
        }
    }

    override fun getItemCount(): Int = listTransaction.size

    private fun formatCurrency(number: Int): String {
        val formatter = DecimalFormat("#,###")
        val formattedNumber = formatter.format(abs(number))

        return if (number < 0) "-Rp$formattedNumber" else "Rp$formattedNumber"
    }
}