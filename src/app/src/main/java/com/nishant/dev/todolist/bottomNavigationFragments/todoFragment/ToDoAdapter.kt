package com.nishant.dev.todolist.bottomNavigationFragments.todoFragment

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.nishant.dev.todolist.R
import com.nishant.dev.todolist.database.ToDo
import com.nishant.dev.todolist.database.ToDoDao
import net.cachapa.expandablelayout.ExpandableLayout
import android.graphics.Paint
import android.util.Log
import android.widget.EditText
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView


class ToDoAdapter(private var inProgressList: MutableList<ToDo>, private val todoDao: ToDoDao):
    RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {

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

        @SuppressLint("NotifyDataSetChanged")
        fun bind() {

            val data = inProgressList[adapterPosition]

            // Set Title.
            val taskTitle = itemView.findViewById<TextView>(R.id.taskTitle)
            taskTitle.text = data.task_title

            // Set task description.
            val taskDescription = itemView.findViewById<TextView>(R.id.TodoTaskDescription)
            taskDescription.text = data.task_description

            val taskCheckbox = itemView.findViewById<CheckBox>(R.id.markAsDoneCheckbox)

            taskCheckbox.isChecked = data.done
            // Strikethrough title.
            if (data.done) {
                taskTitle.paintFlags = taskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
            else {
                taskTitle.paintFlags = taskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }


            // Set listener for checkbox.
            taskCheckbox.setOnCheckedChangeListener { compoundButton, b ->

                // Change in data.
                data.done = b

                // Push changes to db using dao.
                todoDao.updateTask(data)

                // Strikethrough title.
                if (b) {

                    // Add task to end of list.
                    inProgressList.add(inProgressList.size, data)

                    // Remove the old item from list.
                    inProgressList.removeAt(adapterPosition)

                    // Add to end.
                    notifyItemMoved(adapterPosition, inProgressList.size-1)

                    taskTitle.paintFlags = taskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
                else {

                    // Get location from db.
                    val tasksList = todoDao.getTasks()
                    var indexOfItem: Int = 0
                    tasksList.forEach { it ->
                        Log.d("Lol", it.toString())

                        if (it.id == data.id) {

                            // Move item to the it.id index.

                            // Add task to end of list.
                            inProgressList.add(indexOfItem, data)

                            // Remove the old item from list.
                            inProgressList.removeAt(adapterPosition)

                            // Add to end.
                            notifyItemMoved(adapterPosition, indexOfItem)

                        }

                        indexOfItem += 1
                    }

                    taskTitle.paintFlags = taskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }

            }

            // Set listener for edit button.
            val editButton = itemView.findViewById<MaterialButton>(R.id.inProgressEditTask)
            editButton.setOnClickListener {

                // Open bottom sheet.
                // Save changes to db.
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

            // Set listener for move button.
            val moveButton = itemView.findViewById<MaterialButton>(R.id.moveToTodoButton)
            moveButton.setOnClickListener {

                // Update value in list.
                data.archived = true

                // Set value in database.
                todoDao.updateTask(data)

                // Remove item from list.
                inProgressList.removeAt(adapterPosition)

                // NotifyItemMoved.
                notifyItemRemoved(adapterPosition)
            }

            // Set listener for delete button.
            val deleteButton = itemView.findViewById<MaterialButton>(R.id.inProgressDeleteTask)
            deleteButton.setOnClickListener {

                // Delete from database.
                todoDao.deleteTask(data)

                // Remove from list.
                inProgressList.removeAt(adapterPosition)

                // NotifyItemDeleted.
                notifyItemRemoved(adapterPosition)
            }
        }
    }
}