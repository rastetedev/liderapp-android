package com.liderman.mundolidermanapp.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.contact.DirectoryEntity
import com.liderman.mundolidermanapp.presentation.extensions.inflate
import kotlinx.android.synthetic.main.item_directory.view.*

class DirectoryAdapter(private val listener: (DirectoryEntity) -> Unit) : RecyclerView.Adapter<DirectoryAdapter.CommentHolder>() {
    var data: List<DirectoryEntity> = arrayListOf()

    override fun onBindViewHolder(holder: CommentHolder, position: Int) = holder.bind(data[position], listener)

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CommentHolder.init(parent)

    override fun getItemViewType(position: Int) = if (position == 0) 0 else 1

    class CommentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(item: DirectoryEntity, listener: (DirectoryEntity) -> Unit) = with(itemView) {
            setOnClickListener { listener(item) }

            Glide.with(this)
                .load(item.icon)
                .placeholder(R.drawable.ic_0_document_icon)
                .into(img_document)

            title_document.text = item.label
        }

        companion object {
            fun init(parent: ViewGroup) : CommentHolder {
                val view = parent.inflate(R.layout.item_directory)

                return CommentHolder(view)
            }
        }
    }
}