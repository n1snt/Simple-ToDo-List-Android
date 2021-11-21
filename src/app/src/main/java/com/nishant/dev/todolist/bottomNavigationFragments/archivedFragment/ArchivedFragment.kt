package com.nishant.dev.todolist.bottomNavigationFragments.archivedFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nishant.dev.todolist.R
import com.nishant.dev.todolist.database.ToDo
import com.nishant.dev.todolist.database.ToDoDao

class ArchivedFragment(dbDao: ToDoDao) : Fragment() {

    // Get dao to access database.
    val dbDao = dbDao
    var doneList: MutableList<ToDo>? = null
    lateinit var archivedAdapter: archivedTasksListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inf =  inflater.inflate(R.layout.fragment_done, container, false)

        doneList = dbDao.getArchivedTasks()
        Log.d("DoneTasks", doneList.toString())

        // Send data to recyclerview adapter.
        val tasksRecyclerView = inf.findViewById<RecyclerView>(R.id.doneRecyclerView)

        archivedAdapter = archivedTasksListAdapter(doneList!!, dbDao)

        tasksRecyclerView.layoutManager = LinearLayoutManager(context)
        tasksRecyclerView.adapter = archivedAdapter

        return inf
    }
}