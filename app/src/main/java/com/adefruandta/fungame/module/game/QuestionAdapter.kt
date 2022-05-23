package com.adefruandta.fungame.module.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adefruandta.fungame.ImageRendering
import com.adefruandta.fungame.databinding.ItemQuestionBinding

class QuestionAdapter : RecyclerView.Adapter<QuestionViewHolder>() {

    var questions: List<ImageRendering> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder(
            ItemQuestionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        questions[position].render(holder.itemBinding.imageView)
    }

    override fun getItemCount(): Int {
        return questions.size
    }
}

class QuestionViewHolder(val itemBinding: ItemQuestionBinding) :
    RecyclerView.ViewHolder(itemBinding.root)