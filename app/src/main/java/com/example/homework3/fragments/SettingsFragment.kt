package com.example.homework3.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.homework3.R
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val switch: SwitchMaterial = view.findViewById(R.id.themeSwitch)

        val isDark = AppCompatDelegate.getDefaultNightMode() ==
                AppCompatDelegate.MODE_NIGHT_YES
        switch.isChecked = isDark

        switch.setOnCheckedChangeListener { _, isChecked ->
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked)
                    AppCompatDelegate.MODE_NIGHT_YES
                else
                    AppCompatDelegate.MODE_NIGHT_NO
            )
        }
    }
}
