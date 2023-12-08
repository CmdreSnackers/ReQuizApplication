package com.sw.requizapplication.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sw.requizapplication.data.models.Quiz
import com.sw.requizapplication.databinding.LayoutItemBinding

class QuizAdp(
    private var exams: List<Quiz>,
    private val fileN: String
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(child: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = LayoutItemBinding.inflate(
            LayoutInflater.from(child.context),
            child,
            false
        )
        return QuizViewHolder(binding)
    }

    override fun getItemCount() = exams.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val exam = exams[position]
        if (holder is QuizViewHolder) {
            holder.bind(exam)
        }
    }

    fun setQuiz(exam: List<Quiz>) {
        this.exams = exam
        notifyDataSetChanged()
    }

    inner class QuizViewHolder(
        private val binding: LayoutItemBinding
    ) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(quiz: Quiz) {
            binding.run {
                quizId.text = "Quiz Id: ${quiz.QuizId}"
                quizTitle.text = "Title: ${quiz.title}"
                quizDate.text = "Created By: ${quiz.creator}"
            }
        }
    }
}