package com.nishant.dev.todolist.bottomNavigationFragments.archivedFragment

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

class ArchivedTasksListAdapter(private val archivedList: MutableList<ToDo>, private val todoDao: ToDoDao):
    RecyclerView.Adapter<ArchivedTasksListAdapter.ViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArchivedTasksListAdapter.ViewHolder {

        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_archived_task , parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArchivedTasksListAdapter.ViewHolder, position: Int) {

        val taskOptionsView = holder.itemView.findViewById<ExpandableLayout>(R.id.inProgressTaskOptions)
        holder.itemView.setOnClickListener {
            taskOptionsView.toggle()
        }

        holder.bind()
    }

    override fun getItemCount(): Int {
        return archivedList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {

        fun bind() {

            val data = archivedList[adapterPosition]

            // Set Title.
            val taskTitle = itemView.findViewById<TextView>(R.id.taskTitle)
            taskTitle.text = data.task_title

            // Set task description.
            val taskDescription = itemView.findViewById<TextView>(R.id.TodoTaskDescription)
            taskDescription.text = data.task_description

            // Onlick listener for unarchive.

            // Listener for delete.

            // Listener for edit.

        }
    }
}