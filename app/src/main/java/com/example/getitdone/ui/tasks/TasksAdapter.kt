package com.example.getitdone.ui.tasks

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.getitdone.data.Task
import com.example.getitdone.databinding.ItemTaskBinding
import com.google.android.material.checkbox.MaterialCheckBox

class TasksAdapter(private val listener: TaskUpdatedListener) :
    RecyclerView.Adapter<TasksAdapter.ViewHolder>() {
    private var tasks: List<Task> = listOf()

    override fun getItemCount() = tasks.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTasks(tasks: List<Task>){

        this.tasks = tasks.sortedBy { it.isComplete }

        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.checkBox.isChecked = task.isComplete
            binding.toggleStar.isChecked = task.isStarred

            if (task.isComplete) {
                binding.textViewTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                binding.textViewDetails.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }else{
                binding.textViewTitle.paintFlags = 0
                binding.textViewDetails.paintFlags = 0
            }
            binding.textViewTitle.text = task.title
            binding.textViewDetails.text = task.description

            binding.checkBox.setOnClickListener {
                val updatedTask = task.copy(isComplete = binding.checkBox.isChecked)
                listener.onTaskUpdated(updatedTask)
            }
            binding.toggleStar.setOnClickListener {
                val updatedTask = task.copy(isStarred = binding.toggleStar.isChecked)
                listener.onTaskUpdated(updatedTask)
            }
        }
    }

    interface TaskUpdatedListener {
        fun onTaskUpdated(task: Task)
    }
}