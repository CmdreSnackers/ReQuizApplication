package com.sw.requizapplication.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sw.requizapplication.data.models.Mark
import com.sw.requizapplication.databinding.LayoutLeaderBinding

class ScoreAdp(
    private var mark: List<Mark>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(child: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = LayoutLeaderBinding.inflate(
            LayoutInflater.from(child.context),
            child,
            false
        )
        return ScoreViewHolder(binding)
    }

    override fun getItemCount() = mark.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val result = mark[position]
        if (holder is ScoreAdp.ScoreViewHolder) {
            holder.bind(result)
        }
    }

    fun setScore(score: List<Mark>) {
        this.mark = score
        notifyDataSetChanged()

    }

    inner class ScoreViewHolder(
        private val binding: LayoutLeaderBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(mark: Mark) {
            binding.run {
                markUser.text = mark.name
                markedScore.text = mark.result
                markedQuizId.text = mark.quizId

            }
        }
    }
}