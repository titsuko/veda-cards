package com.example.vedaapplication

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.vedaapplication.local.SettingsManager
import com.example.vedaapplication.local.TokenManager
import com.example.vedaapplication.ui.navigation.Navigation
import com.example.vedaapplication.ui.navigation.Screen
import com.example.vedaapplication.ui.theme.VedaApplicationTheme
import com.example.vedaapplication.util.LocaleHelper

class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
        val settingsManager = SettingsManager(newBase)
        super.attachBaseContext(LocaleHelper.onAttach(newBase, settingsManager.getLanguage()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val settingsManager = SettingsManager(this)
        val tokenManager = TokenManager(this)

        val startDestination = if (tokenManager.isUserLoggedIn) {
            Screen.HomeGraph.route
        } else {
            Screen.AuthGraph.route
        }

        setContent {
            val isDarkTheme = remember {
                mutableStateOf(settingsManager.isDarkTheme())
            }

            VedaApplicationTheme(darkTheme = isDarkTheme.value) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    Navigation(
                        navController = navController,
                        startDestination = startDestination
                    )
                }
            }
        }
    }
}