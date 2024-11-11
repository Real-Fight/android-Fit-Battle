package com.qpeterp.fitbattle.presentation.root.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.qpeterp.fitbattle.presentation.features.auth.login.screen.LoginScreen
import com.qpeterp.fitbattle.presentation.features.auth.register.screen.RegisterCompleteScreen
import com.qpeterp.fitbattle.presentation.features.auth.register.screen.RegisterIdScreen
import com.qpeterp.fitbattle.presentation.features.auth.register.screen.RegisterNameScreen
import com.qpeterp.fitbattle.presentation.features.auth.register.screen.RegisterPasswordScreen
import com.qpeterp.fitbattle.presentation.features.auth.register.viewmodel.RegisterViewModel
import com.qpeterp.fitbattle.presentation.features.battle.screen.LoadingScreen
import com.qpeterp.fitbattle.presentation.features.battle.screen.MuscleBattleScreen
import com.qpeterp.fitbattle.presentation.features.main.battle.screen.BattleScreen
import com.qpeterp.fitbattle.presentation.features.main.home.screen.HomeScreen
import com.qpeterp.fitbattle.presentation.features.main.profile.screen.ProfileScreen
import com.qpeterp.fitbattle.presentation.features.main.rank.screen.RankingScreen
import com.qpeterp.fitbattle.presentation.features.main.screen.MainScreen
import com.qpeterp.fitbattle.presentation.features.main.viewmodel.MainViewModel
import com.qpeterp.fitbattle.presentation.features.setting.screen.SettingScreen
import com.qpeterp.fitbattle.presentation.features.setting.viewModel.SettingViewModel
import com.qpeterp.fitbattle.presentation.features.train.screen.TrainScreen

@ExperimentalMaterial3Api
@Composable
fun NavigationGraph(
    navController: NavHostController,
    isUserLoggedIn: Boolean
) {
    val registerViewModel: RegisterViewModel = hiltViewModel()
    val settingViewModel: SettingViewModel = hiltViewModel()
    val mainViewModel: MainViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = if (isUserLoggedIn) NavGroup.Main.MAIN else NavGroup.Auth.LOGIN
    ) {
        // Auth 그룹 네비게이션
        composable(NavGroup.Auth.LOGIN) {
            LoginScreen(navController)
        }

        composable(NavGroup.Auth.REGISTER_ID) {
            RegisterIdScreen(navController, registerViewModel)
        }

        composable(NavGroup.Auth.REGISTER_PASSWORD) {
            RegisterPasswordScreen(navController, registerViewModel)
        }

        composable(NavGroup.Auth.REGISTER_NAME) {
            RegisterNameScreen(navController, registerViewModel)
        }

        composable(NavGroup.Auth.REGISTER_COMPLETE) {
            RegisterCompleteScreen(navController)
        }

        // Main 그룹 네비게이션
        composable(NavGroup.Main.MAIN) {
            MainScreen(navController, mainViewModel)
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

        // Feature 그룹 네비게이션
        composable(NavGroup.Feature.SETTING) {
            SettingScreen(navController, settingViewModel)
        }

        composable(NavGroup.Feature.TRAIN) {
            TrainScreen(navController)
        }

        composable(NavGroup.Feature.LOADING) {
            LoadingScreen(navController)
        }

        composable(NavGroup.Feature.MUSCLE_BATTLE) {
            MuscleBattleScreen(navController)
        }
    }
}