package com.example.vedaapplication.ui.screen.settings.state

import com.example.vedaapplication.remote.model.response.AccountResponse

data class SettingsState(
    val profile: AccountResponse? = null,
    val isLoading: Boolean = true,
    val showLogoutDialog: Boolean = false,
    val isDarkTheme: Boolean = false,
    val currentLangCode: String = "ru"
) {
    val currentLangLabel: String
        get() = if (currentLangCode == "ru") "Русский" else "English"
}