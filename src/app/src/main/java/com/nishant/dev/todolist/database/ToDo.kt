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
    @ColumnInfo(name = "task_title") var task_title: String?,
    @ColumnInfo(name = "task_description") var task_description: String?,

    // Is task done?
    @ColumnInfo(name="done") var done: Boolean = false,

    // Is task archived?
    @ColumnInfo(name="archived") var archived: Boolean = false,
)