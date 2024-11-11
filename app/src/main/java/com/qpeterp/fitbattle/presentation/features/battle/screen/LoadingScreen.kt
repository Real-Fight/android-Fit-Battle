package com.qpeterp.fitbattle.presentation.features.battle.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.qpeterp.fitbattle.presentation.extensions.fitBattleClickable
import com.qpeterp.fitbattle.presentation.features.battle.viewmodel.LoadingViewModel
import com.qpeterp.fitbattle.presentation.theme.Colors

@Composable
fun LoadingScreen(
    navController: NavController,
    viewModel: LoadingViewModel = hiltViewModel(),
) {
    val matchingState by viewModel.matchingState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.White),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            if (!matchingState) {
                Icon(
                    imageVector = Icons.Outlined.Logout,
                    contentDescription = "icon to match cancel",
                    tint = Colors.Red,
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                        .size(36.dp)
                        .fitBattleClickable {
                            viewModel.matchingCancel()
                            navController.navigate("main") {
                                popUpTo(0)
                            }
                        }
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "애니메이션",
                color = Colors.Black,
                fontSize = 40.sp
            )
            Text(
                text = if (matchingState) "운동한판!" else "매칭 중...",
                color = if (matchingState) Colors.LightPrimaryColor else Colors.Black,
                fontSize = 28.sp
            )
        }

        Text(
            text = "Tip. 푸시업바를 사용하면서 더욱 깊이 내려가는 것은 어깨 관절과 등에 부하가 가해지기 때문에 피하는 것이 좋습니다.",
            fontSize = 16.sp,
            modifier = Modifier
                .widthIn(max = 300.dp)
                .padding(bottom = 120.dp)
        )
    }

    if (matchingState) {
        viewModel.readiedGame()
        navController.navigate("muscleBattle")
        viewModel.setMatchingState()
    }
}
