package com.nishant.dev.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nishant.dev.todolist.bottomNavigationFragments.doneFragment.DoneFragment
import com.nishant.dev.todolist.bottomNavigationFragments.settingsFragment.SettingsFragment
import com.nishant.dev.todolist.bottomNavigationFragments.todoFragment.TodoFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        // Hide action bar.
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val doneFragment = DoneFragment()
        val settingsFragment = SettingsFragment()
        val todoFragment = TodoFragment()

        // Initialize activity by setting the default launch fragment to
        // TodoFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.bottom_nav_fragment_container, todoFragment)
            .commit()

        // Set listener for bottom nav bar.
        val navBar = findViewById<BottomNavigationView>(R.id.bottom_nav_view)

        navBar.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.done_bottom_nav -> {
                    setFragment(doneFragment)
                }
                R.id.todo_bottom_nav -> {
                    setFragment(todoFragment)
                }
                R.id.settings_bottom_nav -> {
                    setFragment(settingsFragment)
                }
                else -> false
            }
        }
    }

    private fun setFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.bottom_nav_fragment_container, fragment)
            .commit()

        return true
    }
}