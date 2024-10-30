package com.qpeterp.fitbattle.presentation.features.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.qpeterp.fitbattle.presentation.features.main.home.screen.HomeScreen
import com.qpeterp.fitbattle.presentation.features.main.home.viewmodel.HomeViewModel
import com.qpeterp.fitbattle.presentation.features.main.viewmodel.MainViewModel
import com.qpeterp.fitbattle.presentation.root.navigation.NavGroup
import com.qpeterp.fitbattle.presentation.theme.Colors

@ExperimentalMaterial3Api
@Composable
fun MainScreen(
    navController: NavController, // 외부에서 전달된 navController 사용
) {
    // NavHostController 생성
    val mainNavController = rememberNavController()
    var selectedItem by remember { mutableStateOf(NavGroup.Main.HOME) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        when (selectedItem) {
                            NavGroup.Main.HOME -> "홈"
                            NavGroup.Main.BATTLE -> "운동한판"
                            NavGroup.Main.RANKING -> "운동랭킹"
                            NavGroup.Main.PROFILE -> "프로필"
                            else -> ""
                        },
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                modifier = Modifier.fillMaxWidth(),
            )
        },
        bottomBar = {
            MyBottomNavigation(mainNavController) {
                selectedItem = it
            }
        } // mainNavController를 하위 NavController에 사용
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Colors.White)
        ) {
            MainNavHost(mainNavController) // 하위 NavHostController 사용
        }
    }
}

@Composable
fun MainNavHost(navController: NavHostController) {
    val homeViewModel: HomeViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = NavGroup.Main.HOME
    ) {
        composable(NavGroup.Main.HOME) {
            HomeScreen(navController, homeViewModel)
        }
//        composable(NavGroup.Main.BATTLE) { BattleScreen() }
//        composable(NavGroup.Main.RANKING) { RankingScreen() }
//        composable(NavGroup.Main.PROFILE) { ProfileScreen() }
    }
}

@Composable
fun MyBottomNavigation(navController: NavController, selectItem: (String) -> Unit) {
    val items = listOf(
        NavGroup.Main.HOME,
        NavGroup.Main.BATTLE,
        NavGroup.Main.RANKING,
        NavGroup.Main.PROFILE
    )
    // 선택된 아이템을 저장할 상태 변수
    var selectedItem by remember { mutableStateOf(NavGroup.Main.HOME) }

    NavigationBar(
        containerColor = Colors.White,
        contentColor = Colors.White
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = when (item) {
                            NavGroup.Main.HOME -> Icons.Outlined.Home
                            NavGroup.Main.BATTLE -> Icons.Outlined.FitnessCenter
                            NavGroup.Main.RANKING -> Icons.Outlined.BarChart
                            NavGroup.Main.PROFILE -> Icons.Outlined.Person
                            else -> return@NavigationBarItem
                        },
                        contentDescription = item
                    )
                },
                label = {
                    Text(
                        text = when (item) {
                            NavGroup.Main.HOME -> "홈"
                            NavGroup.Main.BATTLE -> "한판"
                            NavGroup.Main.RANKING -> "랭킹"
                            NavGroup.Main.PROFILE -> "프로필"
                            else -> return@NavigationBarItem
                        },
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black // 텍스트 색상
                        )
                    )
                },
                selected = selectedItem == item, // 현재 선택된 아이템 확인
                onClick = {
                    selectedItem = item // 클릭 시 선택된 아이템 업데이트
                    selectItem(selectedItem)
                    navController.navigate(item) {
                        // 모든 기존 스택을 지우고 새 목적지로 이동
                        popUpTo(NavGroup.Main.MAIN) { inclusive = true }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Colors.LightPrimaryColor, // 선택된 아이콘 색상
                    unselectedIconColor = Color.Black, // 비선택 아이콘 색상
                    selectedTextColor = Color.White, // 선택된 텍스트 색상
                    unselectedTextColor = Color.Gray, // 비선택 텍스트 색상
                    indicatorColor = Colors.DarkPrimaryColorLight
                )
            )
        }
    }
}