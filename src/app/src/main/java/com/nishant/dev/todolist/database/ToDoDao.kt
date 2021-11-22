package com.nishant.dev.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ToDoDao {

    @Insert
    fun addTask(todo: ToDo)

    @Query("SELECT * FROM todo WHERE archived IS 0 ORDER BY done ASC, rowid ASC")
    fun getTasks(): MutableList<ToDo>

    @Query("SELECT * FROM todo WHERE archived IS 1 ORDER BY rowid DESC")
    fun getArchivedTasks(): MutableList<ToDo>

    @Update()
    fun updateTask(todo: ToDo)

    @Delete()
    fun deleteTask(todo: ToDo)

}