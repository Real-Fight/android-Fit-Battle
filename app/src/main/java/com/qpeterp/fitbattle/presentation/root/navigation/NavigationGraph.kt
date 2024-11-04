package com.qpeterp.fitbattle.presentation.root.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.qpeterp.fitbattle.presentation.features.auth.login.screen.LoginScreen
import com.qpeterp.fitbattle.presentation.features.auth.register.screen.RegisterCompleteScreen
import com.qpeterp.fitbattle.presentation.features.auth.register.screen.RegisterIdScreen
import com.qpeterp.fitbattle.presentation.features.auth.register.screen.RegisterNameScreen
import com.qpeterp.fitbattle.presentation.features.auth.register.screen.RegisterPasswordScreen
import com.qpeterp.fitbattle.presentation.features.main.battle.screen.BattleScreen
import com.qpeterp.fitbattle.presentation.features.main.home.screen.HomeScreen
import com.qpeterp.fitbattle.presentation.features.main.profile.screen.ProfileScreen
import com.qpeterp.fitbattle.presentation.features.main.ranking.screen.RankingScreen
import com.qpeterp.fitbattle.presentation.features.main.screen.MainScreen

@ExperimentalMaterial3Api
@Composable
fun NavigationGraph(
    navController: NavHostController,
    isUserLoggedIn: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = if (isUserLoggedIn) NavGroup.Main.MAIN else NavGroup.Auth.LOGIN
    ) {
        // Auth 그룹 네비게이션
        composable(NavGroup.Auth.LOGIN) {
            LoginScreen(navController)
        }

        composable(NavGroup.Auth.REGISTER_ID) {
            RegisterIdScreen(navController)
        }

        composable(NavGroup.Auth.REGISTER_PASSWORD) {
            RegisterPasswordScreen(navController)
        }

        composable(NavGroup.Auth.REGISTER_NAME) {
            RegisterNameScreen(navController)
        }

        composable(NavGroup.Auth.REGISTER_COMPLETE) {
            RegisterCompleteScreen(navController)
        }

        // Main 그룹 네비게이션
        composable(NavGroup.Main.MAIN) {
            MainScreen(navController)
        }

        composable(NavGroup.Main.HOME) {
            HomeScreen(navController)
        }

        composable(NavGroup.Main.BATTLE) {
            BattleScreen(navController)
        }

        composable(NavGroup.Main.RANKING) {
            RankingScreen(navController)
        }

        composable(NavGroup.Main.PROFILE) {
            ProfileScreen(navController)
        }
    }
}