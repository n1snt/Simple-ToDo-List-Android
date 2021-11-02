package com.nishant.dev.todolist.bottomNavigationFragments.todoFragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nishant.dev.todolist.R
import org.w3c.dom.Text

class TodoFragment: Fragment() {

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
                    customView(R.layout.fragment_add_task_dialog, horizontalPadding = true)
                    negativeButton(text="Cancel")
                    positiveButton(text="Add") { dialog ->

                        val titleText = dialog.getCustomView().findViewById<EditText>(R.id.add_task_dialog_title)
                        val descriptionText = dialog.getCustomView().findViewById<EditText>(R.id.add_task_dialog_task_description)
                        addTask(titleText, descriptionText)
                    }
                }
            }
        }
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

    fun addTask(titleTask: EditText, descriptionTask: EditText) {

        // Validate title of task.
        val txtViewsValid = validateTextViews(titleTask)

        // If txtViewsValid true then add task.
        // Else disable add button && show dialog.
        if(txtViewsValid) {
            // Add task to database.
            Log.d("Add task to db", "Plox")
        }
        else if (!txtViewsValid) {
            // Do not add and disable button.
            Log.d("You tryna act", "SUS")
        }
    }
}