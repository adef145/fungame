package com.adefruandta.fungame.module.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adefruandta.fungame.ImageRendering
import com.adefruandta.fungame.databinding.ItemOptionBinding

class OptionAdapter : RecyclerView.Adapter<OptionViewHolder>() {

    var options: List<ImageRendering> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onItemClickListener: ((ImageRendering) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        return OptionViewHolder(
            ItemOptionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        options[position].render(holder.itemBinding.imageView)
        holder.itemBinding.imageView.setOnClickListener {
            onItemClickListener?.invoke(options[position])
        }
    }

    override fun getItemCount(): Int {
        return options.size
    }
}

class OptionViewHolder(val itemBinding: ItemOptionBinding) :
    RecyclerView.ViewHolder(itemBinding.root)