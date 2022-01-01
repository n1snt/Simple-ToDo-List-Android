package com.nishant.dev.todolist

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.nishant.dev.todolist.bottomNavigationFragments.TodoFragment

class MainActivityViewModel: ViewModel() {

    var activeFragment: Fragment = TodoFragment()

    fun activeFragmentSetter(fragment: Fragment) {
        activeFragment = fragment
    }
}