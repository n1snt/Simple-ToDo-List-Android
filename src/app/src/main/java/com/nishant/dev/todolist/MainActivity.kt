package com.nishant.dev.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nishant.dev.todolist.bottomNavigationFragments.doingFragment.DoingFragment
import com.nishant.dev.todolist.bottomNavigationFragments.doneFragment.DoneFragment
import com.nishant.dev.todolist.bottomNavigationFragments.todoFragment.TodoFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        supportActionBar?.title = "In progress"

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val doneFragment = DoneFragment()
        val todoFragment = TodoFragment()
        val doingFragment = DoingFragment()

        // Initialize activity by setting the default launch fragment to
        // TodoFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.bottom_nav_fragment_container, doingFragment)
            .commit()

        // Set listener for bottom nav bar.
        val navBar = findViewById<BottomNavigationView>(R.id.bottom_nav_view)

        navBar.selectedItemId = R.id.doing_bottom_nav

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
                R.id.doing_bottom_nav -> {
                    supportActionBar?.title = "In progress"
                    setFragment(doingFragment)
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