package com.nishant.dev.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nishant.dev.todolist.bottomNavigationFragments.ArchivedFragment
import com.nishant.dev.todolist.bottomNavigationFragments.TodoFragment
import com.nishant.dev.todolist.database.ToDoDatabase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        supportActionBar?.title = "ToDo"

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

        val doneFragment = ArchivedFragment(todoDao)
        val todoFragment = TodoFragment(todoDao)

        // Set listener for bottom nav bar.
        val navBar = findViewById<BottomNavigationView>(R.id.bottom_nav_view)

        // Initialize activity by setting the default launch fragment to
        // TodoFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.bottom_nav_fragment_container, todoFragment)
            .commit()

        navBar.selectedItemId = R.id.todo_bottom_nav

        longPressShortcutListener(doneFragment, todoFragment, navBar)

        navBar.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.archived_bottom_nav -> {
                    supportActionBar?.title = "Archived"
                    setFragment(doneFragment)
                }
                R.id.todo_bottom_nav -> {
                    supportActionBar?.title = "ToDo"
                    setFragment(todoFragment)
                }
                else -> false
            }
        }

        setLongPressShortcuts()
    }

    private fun setFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.popup_enter, R.anim.popup_exit)
            .replace(R.id.bottom_nav_fragment_container, fragment)
            .commit()

        return true
    }

    private fun setLongPressShortcuts() {

        val inProgressShortcut = ShortcutInfoCompat.Builder(this, "inProgressShortcut")
            .setShortLabel("ToDo")
            .setLongLabel("Open todo.")
            .setIntent(
                Intent(this, MainActivity::class.java).setAction("in progress")
            )
            .build()

        val doneShortcut = ShortcutInfoCompat.Builder(this, "doneShortcut")
            .setShortLabel("Archived")
            .setLongLabel("Open archived tasks.")
            .setIntent(
                Intent(this, MainActivity::class.java).setAction("done")
            )
            .build()

        val listTest :MutableList<ShortcutInfoCompat> = ArrayList()
        listTest.add(inProgressShortcut)
        listTest.add(doneShortcut)

        ShortcutManagerCompat.setDynamicShortcuts(this, listTest)
    }

    private fun longPressShortcutListener(
        archivedFragment: ArchivedFragment,
        todoFragment: TodoFragment,
        navBar: BottomNavigationView
    ) {

        val action = if (intent != null) intent.action else null
        if (action != null) {

            when (action) {
                "todo" -> {

                    // Set title.
                    supportActionBar?.title = "ToDo"

                    // Set fragment.
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.bottom_nav_fragment_container, todoFragment)
                        .commit()

                    navBar.selectedItemId = R.id.todo_bottom_nav
                }
                "done" -> {

                    // Set title.
                    supportActionBar?.title = "Done"

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.bottom_nav_fragment_container, archivedFragment)
                        .commit()

                    navBar.selectedItemId = R.id.archived_bottom_nav
                }
            }
        }
    }
}