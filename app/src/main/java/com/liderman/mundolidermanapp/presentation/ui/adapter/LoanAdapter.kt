package com.liderman.mundolidermanapp.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.borrow.BorrowEntity
import com.liderman.mundolidermanapp.presentation.extensions.inflate
import kotlinx.android.synthetic.main.item_loans.view.*


class LoanAdapter() : RecyclerView.Adapter<LoanAdapter.CommentHolder>() {
    var data: List<BorrowEntity> = arrayListOf()

    override fun onBindViewHolder(holder: CommentHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CommentHolder.init(parent)

    override fun getItemViewType(position: Int) = if (position == 0) 0 else 1

    class CommentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(item: BorrowEntity) = with(itemView) {

            val rest = item.restAmount.replace(",", ".")
            val borrow = item.borrowAmount.replace(",", ".")

            circularProgressBar.apply {
                setProgressWithAnimation(rest.toFloat(), 1500)
                progressMax = borrow.toFloat()
            }
            type_loan.text = "Tipo: ${item.borrowType}"
            reason_loan.text = "Comentario: ${item.comment.trim()}"
            date_loan.text = "Fecha de Aprobación ${item.approveDate}"
            total_loan.text = "${borrow}\nMonto Prestamo"
            pay_loan.text = "${rest}\nSaldo deuda"
        }

        companion object {
            fun init(parent: ViewGroup): CommentHolder {
                val view = parent.inflate(R.layout.item_loans)

                return CommentHolder(view)
            }
        }
    }
}
