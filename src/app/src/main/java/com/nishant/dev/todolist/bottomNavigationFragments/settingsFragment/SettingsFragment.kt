package com.nishant.dev.todolist.bottomNavigationFragments.settingsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nishant.dev.todolist.R

class SettingsFragment:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inf =  inflater.inflate(R.layout.fragment_settings, container, false)

        // Write code here.
        // here.

        return inf
    }
}