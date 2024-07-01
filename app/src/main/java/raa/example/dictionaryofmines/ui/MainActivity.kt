package raa.example.dictionaryofmines.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import raa.example.dictionaryofmines.R
import raa.example.dictionaryofmines.databinding.ActivityMainBinding
import raa.example.dictionaryofmines.ui.mainFragment.MainFragment
import raa.example.dictionaryofmines.ui.settingFragment.SettingFragment

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            replaceFragment(MainFragment.newInstance())
        }

        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        bindBottomNavBar()

        sharedPreferences = this.getSharedPreferences("AppSettings", 0)
        val isDarkModeEnabled = sharedPreferences.getBoolean("DarkMode", false)

        if (isDarkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun bindBottomNavBar() {
        binding.bottomNavigationBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_main -> {
                    replaceFragment(MainFragment.newInstance())
                }

                R.id.menu_setting -> {
                    replaceFragment(SettingFragment.newInstance())
                }
            }

            return@setOnItemSelectedListener true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .commit()
    }
}