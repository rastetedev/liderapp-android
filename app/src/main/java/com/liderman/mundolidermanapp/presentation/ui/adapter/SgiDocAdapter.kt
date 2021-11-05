package com.liderman.mundolidermanapp.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.sgidoc.SgiDocEntity
import com.liderman.mundolidermanapp.presentation.extensions.inflate
import kotlinx.android.synthetic.main.item_sgi_doc.view.*

class SgiDocAdapter(private val listener: (SgiDocEntity) -> Unit) :
    RecyclerView.Adapter<SgiDocAdapter.CommentHolder>() {
    var data: List<SgiDocEntity> = arrayListOf()

    override fun onBindViewHolder(holder: CommentHolder, position: Int) =
        holder.bind(data[position], listener)

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CommentHolder.init(parent)


    class CommentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(item: SgiDocEntity, listener: (SgiDocEntity) -> Unit) =
            with(itemView) {
                this.apply {
                    background = ContextCompat.getDrawable(
                        this.context,
                        if (layoutPosition % 2 == 0)
                            R.drawable.shape_grey_one
                        else
                            R.drawable.shape_grey_two
                    )
                }

                setOnClickListener { listener(item) }

                Glide.with(this)
                    .load(item.iconUrl)
                    .circleCrop()
                    .into(img_document)

                title_document.text = item.label
            }

        companion object {
            fun init(parent: ViewGroup): CommentHolder {
                val view = parent.inflate(R.layout.item_sgi_doc)
                return CommentHolder(view)
            }
        }
    }
}