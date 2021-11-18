package com.nishant.dev.todolist.bottomNavigationFragments.inProgressFragment.inProgressList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nishant.dev.todolist.R
import com.nishant.dev.todolist.bottomNavigationFragments.todoFragment.todoList.ToDoAdapter
import com.nishant.dev.todolist.database.ToDo
import com.nishant.dev.todolist.database.ToDoDao
import net.cachapa.expandablelayout.ExpandableLayout

class inProgressAdapter(private val inProgressList: MutableList<ToDo>, private val todoDao: ToDoDao):
    RecyclerView.Adapter<inProgressAdapter.ViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_in_progress_task , parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val taskOptionsView = holder.itemView.findViewById<ExpandableLayout>(R.id.inProgressTaskOptions)
        holder.itemView.setOnClickListener {
            taskOptionsView.toggle()
        }

        holder.bind()
    }

    override fun getItemCount(): Int {
        return inProgressList.size
    }

    inner class ViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView)  {
         // Bind stuff to viewholder here.

            fun bind() {

                val data = inProgressList[adapterPosition]

                // Set Title.
                val taskTitle = itemView.findViewById<TextView>(R.id.taskTitle)
                taskTitle.text = data.task_title

                // Set task description.
                val taskDescription = itemView.findViewById<TextView>(R.id.TodoTaskDescription)
                taskDescription.text = data.task_description

            }
        }
}