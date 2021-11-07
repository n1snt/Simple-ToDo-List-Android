package com.nishant.dev.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ToDoDao {

    @Insert
    fun addTask(todo: ToDo)

    @Query("SELECT * FROM todo ORDER BY rowid ASC")
    fun getTasks(): MutableList<ToDo>

    @Query("SELECT * FROM todo WHERE task_done IS 1 ORDER BY rowid ASC")
    fun getDoneTasks(): MutableList<ToDo>

    @Update
    fun markTask(todo: ToDo)

    @Update()
    fun moveTask(todo: ToDo)

    @Delete()
    fun deleteTask(todo: ToDo)
}