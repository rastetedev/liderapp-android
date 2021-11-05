package com.liderman.mundolidermanapp.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.payment.PaymentDetailEntity
import com.liderman.mundolidermanapp.presentation.extensions.inflate
import com.liderman.mundolidermanapp.utils.ifIsEmpty
import kotlinx.android.synthetic.main.item_payment_detail.view.*

class MyTicketsAdapter() : RecyclerView.Adapter<MyTicketsAdapter.MyTicketsHolder>() {
    var data: List<PaymentDetailEntity> = arrayListOf()

    override fun onBindViewHolder(holder: MyTicketsHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyTicketsHolder.init(parent)

    override fun getItemViewType(position: Int) = if (position == 0) 0 else 1

    class MyTicketsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(item: PaymentDetailEntity) = with(itemView) {
            lbl_aportaciones?.text = item.aportaciones.ifIsEmpty()
            lbl_descuentos?.text = item.descuentos.ifIsEmpty()
            lbl_haberes?.text = item.haberes.ifIsEmpty()
            lbl_monto_aportaciones?.text ="S/. " + item.montoAportaciones?.replace(",", ".").ifIsEmpty()
            lbl_monto_descuentos?.text = "S/. " + item.montoDescuentos?.replace(",", ".").ifIsEmpty()
            lbl_monto_haberes?.text = "S/. " + item.montoHaberes?.replace(",", ".").ifIsEmpty()
        }

        companion object {
            fun init(parent: ViewGroup): MyTicketsHolder {
                val view = parent.inflate(R.layout.item_payment_detail)
                return MyTicketsHolder(view)
            }
        }
    }
}