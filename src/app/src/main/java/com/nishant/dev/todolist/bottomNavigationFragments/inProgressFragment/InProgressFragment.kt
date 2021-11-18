package com.nishant.dev.todolist.bottomNavigationFragments.inProgressFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.nishant.dev.todolist.R
import com.nishant.dev.todolist.database.ToDo
import com.nishant.dev.todolist.database.ToDoDao
import com.nishant.dev.todolist.database.ToDoDatabase

class InProgressFragment(val dbDao: ToDoDao): Fragment() {

    private var dbInstance: ToDoDatabase? = null
    private var todoDao = dbDao
    var inProgressList: MutableList<ToDo>? = null
    lateinit var inProgressAdapter: inProgressAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inf =  inflater.inflate(R.layout.fragment_in_progress, container, false)

        inProgressList = todoDao.getInProgressTasks()
        Log.d("DoneTasks", inProgressList.toString())

        // Send data to recyclerview adapter.
        val tasksRecyclerView = inf.findViewById<RecyclerView>(R.id.inProgressRecyclerView)

        Log.d("List",inProgressList.toString())

        inProgressAdapter = inProgressAdapter(inProgressList!!, dbDao)

        tasksRecyclerView.layoutManager = LinearLayoutManager(context)
        tasksRecyclerView.adapter = inProgressAdapter

        return inf
    }
}