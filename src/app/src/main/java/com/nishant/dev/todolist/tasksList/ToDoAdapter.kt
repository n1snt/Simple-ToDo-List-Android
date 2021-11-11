package com.nishant.dev.todolist.tasksList

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.button.MaterialButton
import com.nishant.dev.todolist.R
import com.nishant.dev.todolist.database.ToDo
import com.nishant.dev.todolist.database.ToDoDao
import com.nishant.dev.todolist.database.ToDoDatabase
import net.cachapa.expandablelayout.ExpandableLayout


class ToDoAdapter(private val todoList: MutableList<ToDo>, private val todoDao: ToDoDao):
    RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {

    var todoListData = todoList

    private var context: Context? = null
    private var mExpandedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoAdapter.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_task , parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToDoAdapter.ViewHolder, position: Int) {

        val taskOptionsView = holder.itemView.findViewById<ExpandableLayout>(R.id.taskOptions)
        holder.itemView.setOnClickListener {
            taskOptionsView.toggle()
        }

        holder.bind()
    }

    override fun getItemCount(): Int {
        return todoList.size
    }


    inner class ViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView)  {

            @SuppressLint("NotifyDataSetChanged")
            fun bind() {

                val data = todoListData[adapterPosition]

                // Set move to in progress button.
                val inProgress = itemView.findViewById<MaterialButton>(R.id.moveToInProgressButton)
                inProgress.setOnClickListener {
                    // Add logic here plox.
                    Log.d("Lmao" ,"Works")
                }

                // Set Title.
                val taskTitle = itemView.findViewById<TextView>(R.id.taskTitle)
                taskTitle.text = data.task_title

                // Set task description.
                val taskDescription = itemView.findViewById<TextView>(R.id.TodoTaskDescription)
                taskDescription.text = data.task_description

                // Setup database instance.
                val dbInstance =
                    context?.let {
                        Room.databaseBuilder(it, ToDoDatabase::class.java, "todo")
                            .allowMainThreadQueries()
                            .build()
                    }

                // Get DAO.
                val todoDao = dbInstance?.todoDao()!!

                // Set edit onclick.
                val editButton = itemView.findViewById<MaterialButton>(R.id.TodoEditTask)
                editButton.setOnClickListener {
                    // Add code here.
                    Log.d("LOL", "lmao")

                    // Launch add task dialog.
                    // Then edit the task from there.
                }

                // Set delete onclick.
                val deleteButton = itemView.findViewById<MaterialButton>(R.id.TodoDeleteTask)
                deleteButton.setOnClickListener {
                    // Add more code here.
                    Log.d("LOL", "This works")

                    // Delete from database.
                    // Delete by passing todoobj from todolistdata.
                    todoDao.deleteTask(data)

                    // Delete from list.
                    todoListData.removeAt(adapterPosition)

                    // Notify adapter that item is removed & the range of list is changed.
                    notifyItemRemoved(adapterPosition)
                }
            }
        }
    }