package com.example.tasks_dhruv.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks_dhruv.R
import com.example.tasks_dhruv.models.Task

class TaskDataAdapter(var taskListData: List<Task>, var deleteTask: (Int)->Unit) : RecyclerView.Adapter<TaskDataAdapter.TaskDataViewHolder>() {

    inner class TaskDataViewHolder(itemView: View): RecyclerView.ViewHolder (itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskDataViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.task_row_layout, parent, false)
        return TaskDataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskListData.size
    }

    override fun onBindViewHolder(holder: TaskDataViewHolder, position: Int) {
        val llMain = holder.itemView.findViewById<LinearLayout>(R.id.llMain)
        val tvTaskName = holder.itemView.findViewById<TextView>(R.id.tvTaskName)
        val ivPriority = holder.itemView.findViewById<ImageView>(R.id.ivPriority)
        val ivDelete = holder.itemView.findViewById<ImageView>(R.id.ivDelete)

        val context = holder.itemView.context
        val task = taskListData.get(position)

        tvTaskName.text = task.taskName

        if(task.isHighPriority) {
            ivPriority.visibility = View.VISIBLE
        } else {
            ivPriority.visibility = View.GONE
        }

        ivDelete.setOnClickListener {
            deleteTask(position)
        }
    }
}