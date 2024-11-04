package com.qpeterp.fitbattle.presentation.root.main.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.qpeterp.fitbattle.presentation.root.navigation.NavigationGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApp()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyApp() {
        val navController = rememberNavController()
        val isUserLoggedIn = remember { mutableStateOf(false) }

        NavigationGraph(
            navController = navController,
            isUserLoggedIn = isUserLoggedIn.value
        )
    }
}
