package com.nishant.dev.todolist.bottomNavigationFragments.todoFragment.todoList

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.google.android.material.button.MaterialButton
import com.nishant.dev.todolist.R
import com.nishant.dev.todolist.database.ToDo
import com.nishant.dev.todolist.database.ToDoDao
import net.cachapa.expandablelayout.ExpandableLayout


class ToDoAdapter(private val todoList: MutableList<ToDo>, private val todoDao: ToDoDao):
    RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {

    var todoListData = todoList

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoAdapter.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_todo_task , parent, false)
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

                // Set Title.
                val taskTitle = itemView.findViewById<TextView>(R.id.taskTitle)
                taskTitle.text = data.task_title

                // Set task description.
                val taskDescription = itemView.findViewById<TextView>(R.id.TodoTaskDescription)
                taskDescription.text = data.task_description

                // Set edit onclick.
                val editButton = itemView.findViewById<MaterialButton>(R.id.TodoEditTask)
                editButton.setOnClickListener {
                    // Add code here.

                    // Launch edit task dialog.
                    context?.let { it1 ->
                        MaterialDialog(it1, BottomSheet(LayoutMode.WRAP_CONTENT)).show {

                            // Set custom view.
                            customView(R.layout.fragment_task_dialog, horizontalPadding = true)

                            // Set title in the custom view.
                            findViewById<TextView>(R.id.fragmentTaskTitle).text = "Edit task"

                            // Set task title and description from DB to edit.
                            val taskTitleEditTxt = findViewById<EditText>(R.id.add_task_dialog_title)
                            taskTitleEditTxt.setText(data.task_title)
                            val taskDescEditTxt = findViewById<EditText>(R.id.add_task_dialog_task_description)
                            taskDescEditTxt.setText(data.task_description)

                            negativeButton(text="Cancel")
                            positiveButton(text="Edit") { dialog ->

                                // Get edited text from edittext.
                                Log.d("Title text", taskTitleEditTxt.text.toString())
                                Log.d("Desc text", taskDescEditTxt.text.toString())

                                // Change in list.
                                data.task_title = taskTitleEditTxt.text.toString()
                                data.task_description = taskDescEditTxt.text.toString()

                                // Change in DB.
                                todoDao.updateTask(data)

                                // Notify plox.
                                notifyItemChanged(adapterPosition)
                            }
                        }
                    }
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

                // Set move to in progress button.
                val inProgress = itemView.findViewById<MaterialButton>(R.id.moveToInProgressButton)
                inProgress.setOnClickListener {
                    // Add logic here plox.

                    // Update value in list.
                    data.task_in_doing = true

                    // Change value in database.
                    todoDao.updateTask(data)

                    // Remove item from list.
                    todoListData.removeAt(adapterPosition)

                    // Notify adapter that item is removed & the range of list is changed.
                    notifyItemRemoved(adapterPosition)

                    Log.d("Lmao" ,"Works")
                }
            }
        }
    }