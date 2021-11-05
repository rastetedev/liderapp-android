package com.liderman.mundolidermanapp.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.lidermanplay.LidermanPlayEntity
import com.liderman.mundolidermanapp.utils.linkpewview.MetaDataKotlin
import com.liderman.mundolidermanapp.utils.linkpewview.ProcessUrl
import com.liderman.mundolidermanapp.presentation.extensions.inflate
import kotlinx.android.synthetic.main.item_play_liderman.view.*

class LiderPlayAdapter(private val listener: (LidermanPlayEntity) -> Unit) : RecyclerView.Adapter<LiderPlayAdapter.CommentHolder>() {
    var data: List<LidermanPlayEntity> = arrayListOf()

    override fun onBindViewHolder(holder: CommentHolder, position: Int) = holder.bind(data[position], listener)

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CommentHolder.init(parent, viewType)

    override fun getItemViewType(position: Int) = if (position == 0) 0 else 1

    class CommentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(item: LidermanPlayEntity, listener: (LidermanPlayEntity) -> Unit) = with(itemView) {
            ProcessUrl(object : ProcessUrl.DoStuff {
                override fun getContext() = context
                override fun done(result: MetaDataKotlin) {
                    try {
                        when (result.typeError) {
                            0 -> {
                                title_item.text = result.title ?: ""
                                sub_title_item.text = result.description ?: ""
                                Glide.with(this@with)
                                    .load(result.imageUrl)
                                    .into(image_url_video)
                            }
                            else -> {}
                        }
                    } catch (e: Exception) {

                    }
                }

            }).execute(item.videoUrl)
            setOnClickListener { listener(item) }
            
        }

        companion object {
            fun init(parent: ViewGroup, viewType: Int) : LiderPlayAdapter.CommentHolder {
                val view = parent.inflate(R.layout.item_play_liderman)

                return LiderPlayAdapter.CommentHolder(view)
            }
        }
    }
}