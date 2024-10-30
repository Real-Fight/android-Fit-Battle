package com.qpeterp.fitbattle.presentation.features.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.qpeterp.fitbattle.presentation.features.main.viewmodel.MainViewModel
import com.qpeterp.fitbattle.presentation.root.navigation.NavGroup
import com.qpeterp.fitbattle.presentation.theme.Colors

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel(),
) {
    Scaffold(
        bottomBar = { MyBottomNavigation(navController) }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Colors.White)
        ) {

        }
    }
}

@Composable
fun MyBottomNavigation(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Outlined.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = false,
            onClick = { navController.navigate(NavGroup.Main.HOME) }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Outlined.Home, contentDescription = "Battle") },
            label = { Text("Battle") },
            selected = false,
            onClick = { navController.navigate(NavGroup.Main.BATTLE) }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Outlined.BarChart, contentDescription = "Ranking") },
            label = { Text("Ranking") },
            selected = false,
            onClick = { navController.navigate(NavGroup.Main.RANKING) }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Outlined.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = false,
            onClick = { navController.navigate(NavGroup.Main.PROFILE) }
        )
    }
}