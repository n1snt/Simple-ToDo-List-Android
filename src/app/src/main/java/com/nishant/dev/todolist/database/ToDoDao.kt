package com.nishant.dev.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ToDoDao {

    @Insert
    fun addTask(todo: ToDo)

    @Query("SELECT * FROM todo WHERE task_in_doing LIKE 0 AND task_done LIKE 0 AND task_in_done LIKE 0 ORDER BY rowid ASC")
    fun getTasks(): MutableList<ToDo>

    @Query("SELECT * FROM todo WHERE task_in_doing IS 1 ORDER BY rowid ASC")
    fun getInProgressTasks(): MutableList<ToDo>

    @Query("SELECT * FROM todo WHERE task_done IS 1 ORDER BY rowid ASC")
    fun getDoneTasks(): MutableList<ToDo>

    @Query("UPDATE todo SET task_in_doing=:doingVal WHERE id=:id")
    fun markDoing(id: Int, doingVal: Boolean)

    @Update()
    fun updateTask(todo: ToDo)

    @Delete()
    fun deleteTask(todo: ToDo)

}