package com.nishant.dev.todolist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class ToDo(
    // Primary key.
    @PrimaryKey(autoGenerate = true) val id: Int?,

    // Task title & description.
    @ColumnInfo(name = "task_title") val task_title: String?,
    @ColumnInfo(name = "task_description") val task_description: String?,

    // Is task marked as done?
    @ColumnInfo(name="task_done") val task_done: Boolean = false,

    // Is task moved to doing?
    @ColumnInfo(name="task_in_doing") var task_in_doing: Boolean = false,

    // Is task moved to done?
    @ColumnInfo(name="task_in_done") val task_in_done: Boolean = false,
)