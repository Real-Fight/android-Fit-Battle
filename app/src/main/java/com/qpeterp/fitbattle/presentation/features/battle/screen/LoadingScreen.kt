package com.qpeterp.fitbattle.presentation.features.battle.screen

import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import app.rive.runtime.kotlin.core.Alignment as RiveAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.qpeterp.fitbattle.R
import com.qpeterp.fitbattle.domain.model.train.TrainType
import com.qpeterp.fitbattle.presentation.core.component.FitBattleDialog
import com.qpeterp.fitbattle.presentation.core.component.RiveAnimation
import com.qpeterp.fitbattle.presentation.extensions.fitBattleClickable
import com.qpeterp.fitbattle.presentation.features.battle.common.BattleConstants
import com.qpeterp.fitbattle.presentation.features.battle.viewmodel.LoadingViewModel
import com.qpeterp.fitbattle.presentation.theme.Colors

@Composable
fun LoadingScreen(
    navController: NavController,
    viewModel: LoadingViewModel = hiltViewModel(),
) {
    val matchingState by viewModel.matchingState.collectAsState()
    var matchingCancelState by remember { mutableStateOf(false) }
    val animation = when (BattleConstants.FIT_TYPE) {
        TrainType.SQUAT -> R.raw.squat
        TrainType.PUSH_UP -> R.raw.push_up
        TrainType.SIT_UP -> R.raw.sit_up
        TrainType.RUN -> R.raw.squat
    }

    BackHandler(enabled = true) {
        matchingCancelState = true
    }

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
                        .fitBattleClickable { matchingCancelState = true }
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            RiveAnimation(
                modifier = Modifier
                    .padding(horizontal = 80.dp)
                    .size(300.dp),
                resId = animation,
                alignment = RiveAlignment.CENTER,
                autoplay = true,
                animationName = "Timeline 1",
                contentDescription = "Just a Rive Animation",
            )
            Text(
                text = if (matchingState) "운동한판!" else "상대 찾는 중...",
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

    // matched 레디 시키게 하기. 레디하면 배틀 화면으로 넘기고, [3,2,1 레디 보내기 -> startGame 받기]

    if (matchingState) {
        viewModel.setMatchingState()
        navController.navigate("muscleBattle")
    }

    FitBattleDialog(
        showDialog = matchingCancelState,
        title = "매칭 취소",
        titleColor = Colors.Black,
        message = "매칭을 취소하시겠습니까?",
        confirmColor = Colors.LightPrimaryColor,
        onDismiss = { matchingCancelState = false },
        onConfirm = {
            matchingCancelState = false
            viewModel.matchingCancel()
            navController.navigate("main") {
                popUpTo(0)
            }
        },
    )
}
