/*
This data class is used by recyclerview adapters in these fragments.
 */

package com.nishant.dev.todolist.bottomNavigationFragments

data class TaskDC(
    val id: Int,
    val title: String,
    val description: String,
    val taskArchived: Boolean,
    val taskDone: Boolean
)