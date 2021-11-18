package com.nishant.dev.todolist.bottomNavigationFragments.todoFragment.todoList

data class TaskDC(

    val id: Int,
    val title: String,
    val description: String,
    val taskDone: Boolean,
    val taskInDone: Boolean
)
