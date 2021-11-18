package com.nishant.dev.todolist.bottomNavigationFragments.doneFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nishant.dev.todolist.R
import com.nishant.dev.todolist.database.ToDo
import com.nishant.dev.todolist.database.ToDoDao
import net.cachapa.expandablelayout.ExpandableLayout

class doneTasksListAdapter(private val doneList: MutableList<ToDo>, private val todoDao: ToDoDao):
    RecyclerView.Adapter<doneTasksListAdapter.ViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): doneTasksListAdapter.ViewHolder {

        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_done_task , parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: doneTasksListAdapter.ViewHolder, position: Int) {

        val taskOptionsView = holder.itemView.findViewById<ExpandableLayout>(R.id.inProgressTaskOptions)
        holder.itemView.setOnClickListener {
            taskOptionsView.toggle()
        }

        holder.bind()
    }

    override fun getItemCount(): Int {
        return doneList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {

        fun bind() {
            // Write code here.

            val data = doneList[adapterPosition]

            // Set Title.
            val taskTitle = itemView.findViewById<TextView>(R.id.taskTitle)
            taskTitle.text = data.task_title

            // Set task description.
            val taskDescription = itemView.findViewById<TextView>(R.id.TodoTaskDescription)
            taskDescription.text = data.task_description

        }
    }
}