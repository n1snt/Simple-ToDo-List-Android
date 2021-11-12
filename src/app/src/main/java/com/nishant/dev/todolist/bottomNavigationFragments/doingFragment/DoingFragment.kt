package com.nishant.dev.todolist.bottomNavigationFragments.doingFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.nishant.dev.todolist.R
import com.nishant.dev.todolist.database.ToDo
import com.nishant.dev.todolist.database.ToDoDao
import com.nishant.dev.todolist.database.ToDoDatabase

class DoingFragment(dbDao: ToDoDao): Fragment() {

    // Get dao to access database.
    val dbDao = dbDao

    private var dbInstance: ToDoDatabase? = null
    private lateinit var todoDao: ToDoDao
    var doingList: MutableList<ToDo>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inf =  inflater.inflate(R.layout.fragment_in_progress, container, false)

        // Setup database instance.
        dbInstance =
            context?.let {
                Room.databaseBuilder(it, ToDoDatabase::class.java, "todo")
                    .allowMainThreadQueries()
                    .build()
            }

        // Get DAO.
        todoDao = dbInstance?.todoDao()!!

        doingList = todoDao.getDoingTasks()
        Log.d("DoneTasks", doingList.toString())

        // Send data to recyclerview adapter.


        return inf
    }
}