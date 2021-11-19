package com.nishant.dev.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nishant.dev.todolist.bottomNavigationFragments.inProgressFragment.InProgressFragment
import com.nishant.dev.todolist.bottomNavigationFragments.doneFragment.DoneFragment
import com.nishant.dev.todolist.bottomNavigationFragments.todoFragment.TodoFragment
import com.nishant.dev.todolist.database.ToDoDatabase

class MainActivity : AppCompatActivity() {

    private var defaultFragment = "in progress"

    override fun onCreate(savedInstanceState: Bundle?) {

        supportActionBar?.title = "In progress"

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup database instance.
        val dbInstance =
            this.let {
                Room.databaseBuilder(it, ToDoDatabase::class.java, "todo")
                    .allowMainThreadQueries()
                    .build()
            }

        // Get DAO.
        val todoDao = dbInstance.todoDao()

        val doneFragment = DoneFragment(todoDao)
        val todoFragment = TodoFragment(todoDao)
        val doingFragment = InProgressFragment(todoDao)

        // Set listener for bottom nav bar.
        val navBar = findViewById<BottomNavigationView>(R.id.bottom_nav_view)

        // Initialize activity by setting the default launch fragment to
        // TodoFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.bottom_nav_fragment_container, doingFragment)
            .commit()

        navBar.selectedItemId = R.id.in_progress_bottom_nav

        longPressShortcutListener(doneFragment, todoFragment, doingFragment, navBar)

        navBar.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.done_bottom_nav -> {
                    supportActionBar?.title = "Done"
                    setFragment(doneFragment)
                }
                R.id.todo_bottom_nav -> {
                    supportActionBar?.title = "Todo"
                    setFragment(todoFragment)
                }
                R.id.in_progress_bottom_nav -> {
                    supportActionBar?.title = "In progress"
                    setFragment(doingFragment)
                }
                else -> false
            }
        }


        setLongPressShortcuts()

    }

    private fun setFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.bottom_nav_fragment_container, fragment)
            .commit()

        return true
    }

    private fun setLongPressShortcuts() {

        val todoShortcut = ShortcutInfoCompat.Builder(this, "todoShortcut")
            .setShortLabel("Todo")
            .setLongLabel("Open todo tasks list.")
            .setIntent(
                Intent(this, MainActivity::class.java).setAction("todo")
            )
            .build()

        val inProgressShortcut = ShortcutInfoCompat.Builder(this, "inProgressShortcut")
            .setShortLabel("In Progress")
            .setLongLabel("Open in progress tasks.")
            .setIntent(
                Intent(this, MainActivity::class.java).setAction("in progress")
            )
            .build()

        val doneShortcut = ShortcutInfoCompat.Builder(this, "doneShortcut")
            .setShortLabel("Done")
            .setLongLabel("Open completed tasks.")
            .setIntent(
                Intent(this, MainActivity::class.java).setAction("done")
            )
            .build()


        val listTest :MutableList<ShortcutInfoCompat> = ArrayList()
        listTest.add(todoShortcut)
        listTest.add(inProgressShortcut)
        listTest.add(doneShortcut)

        ShortcutManagerCompat.setDynamicShortcuts(this, listTest)
    }

    private fun longPressShortcutListener(
        doneFragment: DoneFragment,
        todoFragment: TodoFragment,
        doingFragment: InProgressFragment,
        navBar: BottomNavigationView
    ) {

        val action = if (intent != null) intent.action else null
        if (action != null) {

            when (action) {
                "todo" -> {

                    // Set title.
                    supportActionBar?.title = "Todo"

                    // Set fragment.
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.bottom_nav_fragment_container, todoFragment)
                        .commit()

                    navBar.selectedItemId = R.id.todo_bottom_nav
                }
                "in progress" -> {

                    // Set title.
                    supportActionBar?.title = "In progress"

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.bottom_nav_fragment_container, doingFragment)
                        .commit()

                    navBar.selectedItemId = R.id.in_progress_bottom_nav
                }
                "done" -> {

                    // Set title.
                    supportActionBar?.title = "Done"

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.bottom_nav_fragment_container, doneFragment)
                        .commit()

                    navBar.selectedItemId = R.id.done_bottom_nav
                }
            }
        }

    }

}