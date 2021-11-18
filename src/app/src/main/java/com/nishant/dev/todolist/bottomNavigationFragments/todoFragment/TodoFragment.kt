package com.nishant.dev.todolist.bottomNavigationFragments.todoFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nishant.dev.todolist.R
import com.nishant.dev.todolist.bottomNavigationFragments.todoFragment.todoList.ToDoAdapter
import com.nishant.dev.todolist.database.ToDo
import com.nishant.dev.todolist.database.ToDoDao

class TodoFragment(dbDao: ToDoDao): Fragment() {

    // Get dao to access database.
    val dbDao = dbDao

    lateinit var todoAdapter: ToDoAdapter

    var todoList: MutableList<ToDo>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inf =  inflater.inflate(R.layout.fragment_todo, container, false)

        // Get add task button.
        val addTaskButton = inf.findViewById<FloatingActionButton>(R.id.add_task)

        // Open bottom sheet when addTaskButton gets clicked on.
        addTaskButton.setOnClickListener{
            context?.let { it1 ->
                MaterialDialog(it1, BottomSheet(LayoutMode.WRAP_CONTENT)).show {
                    customView(R.layout.fragment_task_dialog, horizontalPadding = true)
                    negativeButton(text="Cancel")
                    positiveButton(text="Add") { dialog ->

                        val titleText = dialog.getCustomView().findViewById<EditText>(R.id.add_task_dialog_title)
                        val descriptionText = dialog.getCustomView().findViewById<EditText>(R.id.add_task_dialog_task_description)
                        addTask(titleText, descriptionText)
                    }
                }
            }
        }

        val tasksRecyclerView = inf.findViewById<RecyclerView>(R.id.todoRecyclerView)

        // Get tasks from database.
        todoList = dbDao.getTasks()

        Log.d("List",todoList.toString())

        todoAdapter = ToDoAdapter(todoList!!, dbDao)

        tasksRecyclerView.layoutManager = LinearLayoutManager(context)
        tasksRecyclerView.adapter = todoAdapter

        return inf
    }

    private fun validateTextViews(title: EditText): Boolean {

        // Get content of both the textviews.
        val titleText = title.text.isNullOrBlank()
        Log.d("testsd", titleText.toString())

        // Check if title is empty.
        // if empty return false and create a toast message.
        return if (titleText) {
            title.error = "Title cannot be empty."
            Toast.makeText(context, "Title cannot be empty.", Toast.LENGTH_SHORT).show()
            false

        } else {
            title.error = null
            Toast.makeText(context, "Task added.", Toast.LENGTH_SHORT).show()
            true
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addTask(titleTask: EditText, descriptionTask: EditText) {

        // Validate title of task.
        val txtViewsValid = validateTextViews(titleTask)

        // If txtViewsValid true then add task.
        // Else disable add button && show dialog.
        if(txtViewsValid) {
            // Add task to database.
            Log.d("Add task to db", "Plox")

            // Setup data to save in DB.
            val testing = ToDo(
                id=null,
                task_title = titleTask.text.toString(),
                task_description = descriptionTask.text.toString(),
            )

            // Add data to database.
            dbDao.addTask(testing)

            // Add to the db list above.
            todoList?.add(testing)

            // This is testing stuff to retrieve data from database.
            //val todoList: List<ToDo>? = todoDao?.getTasks()
            //val doneTasks: List<ToDo>? = todoDao?.getDoneTasks()

            /*
            This is a patch to fix tasks not appearing properly in recyclerview
            when there are no tasks in recyclerview.
            */
            if (todoList?.size!! == 0) {
                todoAdapter.notifyItemInserted(0)
            }
            else {
                todoList?.size?.minus(1)?.let {
                    todoAdapter.notifyItemInserted(it)
                }
            }

        }
        else if (!txtViewsValid) {
            // Do not add and disable button.
            Log.d("You tryna act", "SUS")
        }
    }
}