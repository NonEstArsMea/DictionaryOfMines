package raa.example.dictionaryofmines.ui.settingFragment

import android.content.SharedPreferences
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import raa.example.dictionaryofmines.R

class SettingFragment : Fragment() {

    companion object {
        fun newInstance() = SettingFragment()
    }

    private lateinit var sharedPreferences: SharedPreferences

    private val viewModel: SettingFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("AppSettings", 0)
        val isDarkModeEnabled = sharedPreferences.getBoolean("DarkMode", false)

        val switchDarkMode: Switch = view.findViewById(R.id.switch_dark_mode)
        val infoButton: Button = view.findViewById(R.id.btn_info)

        switchDarkMode.isChecked = isDarkModeEnabled

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPreferences.edit().putBoolean("DarkMode", true).apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPreferences.edit().putBoolean("DarkMode", false).apply()
            }
        }

        infoButton.setOnClickListener {
            // Здесь вы можете добавить логику для отображения дополнительной информации о приложении
            // Например, открыть новый фрагмент с информацией
        }
    }
}