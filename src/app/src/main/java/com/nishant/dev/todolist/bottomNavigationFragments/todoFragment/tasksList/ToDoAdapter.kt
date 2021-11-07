package com.nishant.dev.todolist.bottomNavigationFragments.todoFragment.tasksList

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nishant.dev.todolist.R
import com.nishant.dev.todolist.database.ToDo
import java.util.ArrayList




class ToDoAdapter(private val todoList: List<ToDo>):
    RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {

    var todoListData = todoList

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoAdapter.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_task , parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToDoAdapter.ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return todoList.size
    }


    inner class ViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView)  {

            fun bind() {

                val data = todoListData[adapterPosition]

                // Set Title.
                val taskTitle = itemView.findViewById<TextView>(R.id.taskTitle)
                taskTitle.text = data.task_title

                // Set checkbox.
                val taskCheckBox = itemView.findViewById<CheckBox>(R.id.checkBoxTask)
                taskCheckBox.isChecked = data.task_done

                // Setup options later.
                // Plox setup options here to delete etc.

            }
        }

}
