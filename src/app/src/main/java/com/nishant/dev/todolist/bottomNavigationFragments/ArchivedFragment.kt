package com.nishant.dev.todolist.bottomNavigationFragments

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

class ArchivedFragment() : Fragment() {

    private lateinit var dbDao: ToDoDao
    private var archivedList: MutableList<ToDo>? = null
    private lateinit var archivedAdapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inf =  inflater.inflate(R.layout.fragment_archived, container, false)

        // Set dao.
        dbDao = context?.let {
            Room.databaseBuilder(it, ToDoDatabase::class.java, "todo")
                .allowMainThreadQueries()
                .build().todoDao()
        }!!

        archivedList = dbDao.getArchivedTasks()
        Log.d("DoneTasks", archivedList.toString())

        // Send data to recyclerview adapter.
        val tasksRecyclerView = inf.findViewById<RecyclerView>(R.id.todoRecyclerView)

        archivedAdapter = ListAdapter(archivedList!!, dbDao, false)

        tasksRecyclerView.layoutManager = LinearLayoutManager(context)
        tasksRecyclerView.adapter = archivedAdapter

        return inf
    }
}