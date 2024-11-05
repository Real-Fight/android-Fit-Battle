package com.qpeterp.fitbattle.presentation.root.main.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.qpeterp.fitbattle.application.MyApplication
import com.qpeterp.fitbattle.application.PreferenceManager
import com.qpeterp.fitbattle.presentation.root.navigation.NavigationGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        MyApplication.prefs = PreferenceManager(application)
        val prefs = MyApplication.prefs

        val isLoggedIn = prefs.token.isNotEmpty()

        setContent {
            MyApp(isLoggedIn)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyApp(
        isUserLoggedIn: Boolean
    ) {
        val navController = rememberNavController()

        NavigationGraph(
            navController = navController,
            isUserLoggedIn = isUserLoggedIn
        )
    }
}
