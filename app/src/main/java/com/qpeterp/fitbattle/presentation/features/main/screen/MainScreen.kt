package com.qpeterp.fitbattle.presentation.features.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.qpeterp.fitbattle.presentation.features.main.battle.screen.BattleScreen
import com.qpeterp.fitbattle.presentation.features.main.home.screen.HomeScreen
import com.qpeterp.fitbattle.presentation.features.main.profile.screen.ProfileScreen
import com.qpeterp.fitbattle.presentation.features.main.rank.screen.RankingScreen
import com.qpeterp.fitbattle.presentation.features.main.viewmodel.MainViewModel
import com.qpeterp.fitbattle.presentation.theme.Colors

@ExperimentalMaterial3Api
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    var selectedItem by remember { mutableStateOf(viewModel.selectedTab) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MyTopAppBar(selectedItem) {
            navController.navigate("setting")
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .zIndex(1f)
                .background(Colors.BackgroundColor)
        ) {
            when (selectedItem) {
                0 -> HomeScreen(navController)
                1 -> BattleScreen(navController)
                2 -> RankingScreen(navController)
                3 -> ProfileScreen(navController)
            }
        }
        MyBottomNavigation(
            selectedItem
        ) {
            viewModel.updateSelectedTab(it)
            selectedItem = it
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(selectedItem: Int, onIconClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = when (selectedItem) {
                    0 -> "홈"
                    1 -> "운동한판"
                    2 -> "운동랭킹"
                    3 -> "프로필"
                    else -> ""
                },
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Colors.BackgroundColor
        ),
        actions = {
            if (selectedItem == 3) {
                IconButton(onClick = { onIconClick() }) {
                    Icon(
                        imageVector = Icons.Outlined.Settings, // 아이콘 리소스 ID
                        contentDescription = "설정 아이콘", // 접근성 텍스트
                        tint = Color.DarkGray, // 아이콘 색상
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun MyBottomNavigation(
    basicSelectedItem: Int,
    selectItem: (Int) -> Unit
) {
    val items = listOf(0, 1, 2, 3)
    // 선택된 아이템을 저장할 상태 변수
    var selectedItem by remember { mutableIntStateOf(basicSelectedItem) }

    NavigationBar(
        containerColor = Colors.White,
        contentColor = Colors.White,
        modifier = Modifier.zIndex(2f)
    ) {
        items.fastForEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = when (item) {
                            0 -> Icons.Filled.Home
                            1 -> Icons.Filled.FitnessCenter
                            2 -> Icons.Filled.BarChart
                            3 -> Icons.Filled.Person
                            else -> return@NavigationBarItem
                        },
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = when (item) {
                            0 -> "홈"
                            1 -> "한판"
                            2 -> "랭킹"
                            3 -> "프로필"
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
                    if (selectedItem == item) return@NavigationBarItem

                    selectedItem = item // 클릭 시 선택된 아이템 업데이트
                    selectItem(selectedItem)
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