package com.liderman.mundolidermanapp.presentation.ui.adapter


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.QualityEntity
import com.liderman.mundolidermanapp.utils.Methods
import com.liderman.mundolidermanapp.presentation.extensions.inflate
import kotlinx.android.synthetic.main.item_dash.view.*

class QualityAdapter : RecyclerView.Adapter<QualityAdapter.CommentHolder>() {
    var data: List<QualityEntity> = arrayListOf()

    override fun onBindViewHolder(holder: CommentHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CommentHolder.init(parent, viewType)

    override fun getItemViewType(position: Int) = if (position == 0) 0 else 1

    class CommentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: QualityEntity) = with(itemView) {
            val widthScreen = Methods.getWidthScreen() - 16 -16 -8 -8
            this.layoutParams.apply {
                width = (widthScreen/5).toInt()
            }
            item_dash_icon.layoutParams.apply {
                width = (widthScreen/8).toInt()
                height = (widthScreen/8).toInt()
            }
            if (item.isGood == null) {
                item_dash_icon.setImageResource(R.drawable.ic_neutral)
            } else {
                item_dash_icon.setImageResource(if(item.isGood!!) R.drawable.ic_happy else R.drawable.ic_sad)
            }
            item_dash_title.text = item.name
            item_dash_title.textSize = 11F
        }

        companion object {
            fun init(parent: ViewGroup, viewType: Int) : QualityAdapter.CommentHolder {
                val view = parent.inflate(R.layout.item_dash)

                return QualityAdapter.CommentHolder(view)
            }
        }
    }
}