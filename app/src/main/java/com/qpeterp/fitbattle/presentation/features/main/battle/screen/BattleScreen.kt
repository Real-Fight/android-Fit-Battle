package com.qpeterp.fitbattle.presentation.features.main.battle.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.qpeterp.fitbattle.R
import com.qpeterp.fitbattle.data.socket.data.MatchType
import com.qpeterp.fitbattle.domain.model.train.TrainType
import com.qpeterp.fitbattle.presentation.core.component.RiveAnimation
import com.qpeterp.fitbattle.presentation.extensions.fitBattleClickable
import com.qpeterp.fitbattle.presentation.features.main.battle.viewmodel.BattleViewModel
import com.qpeterp.fitbattle.presentation.theme.Colors

@Composable
fun BattleScreen(
    navController: NavController,
    viewModel: BattleViewModel = hiltViewModel(),
) {
    val scrollState = rememberScrollState()
    var showDialog by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(-1) }
    var fitMode by remember { mutableStateOf(TrainType.SQUAT) }
    Column(
        modifier = Modifier
            .padding(20.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "근력한판",
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
        )
        BattleCard(
            title = "푸쉬업",
            content = "더 많은 푸쉬업으로 상대를 넘어보세요!",
            battleType = TrainType.PUSH_UP,
        ) {
            fitMode = TrainType.PUSH_UP
            showDialog = true
        }
        BattleCard(
            title = "스쿼트",
            content = "다리 근력을 강화하고 경쟁에서 앞서가세요!",
            battleType = TrainType.SQUAT,
        ) {
            fitMode = TrainType.SQUAT
            showDialog = true
        }
        BattleCard(
            title = "윗몸 일으키기",
            content = "복근을 강철로 코팅하세요!",
            battleType = TrainType.SIT_UP,
        ) {
            fitMode = TrainType.SIT_UP
            showDialog = true
        }

//        Text(
//            text = "유산소한판",
//            fontWeight = FontWeight.SemiBold,
//            fontSize = 20.sp,
//        )
//        BattleCard(
//            title = "달리기",
//            content = "지금 바로 달려서 경쟁에서 승리하세요!",
//            battleType = TrainType.RUN,
//        ) {
//            fitMode = TrainType.RUN
//            showDialog = true
//        }
    }

    if (showDialog) {
        ChoiceBattleModeDialog(
            fitMode = fitMode,
            selectedOption = selectedOption,
            onOptionSelected = { selectedOption = it },
            onCancel = {
                showDialog = false
                selectedOption = -1
            },
            onConfirm = { matchType, fitType ->
                // 확인 시 동작
                viewModel.setMatchType(matchType, fitType)
                showDialog = false
                selectedOption = -1
                navController.navigate("loading") {
                    popUpTo(0)
                }
            }
        )
    }
}

@Composable
private fun BattleCard(
    title: String,
    content: String,
    battleType: TrainType, // 추후 추가 예정
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)) // 컨테이너 외부를 잘라내기
            .background(Colors.GrayDark)
            .height(120.dp)
            .fitBattleClickable { onClick() }
    ) {
        // 배경 원
        Canvas(
            modifier = Modifier
                .matchParentSize()
                .pointerInput(Unit) { // 클릭 이벤트를 무시
                    awaitPointerEventScope {
                        while (true) {
                            awaitPointerEvent()
                        }
                    }
                },
        ) {
            drawCircle(
                color = Colors.GrayLightTransparent, // 원 색상
                radius = 100.dp.toPx(), // 원 크기
                center = Offset(size.width * 0.8f, size.height * 0.1f) // 오른쪽 위 모서리로 원 위치 조정
            )
        }

        val animation = when (battleType) {
            TrainType.SQUAT -> {
                R.raw.squat
            }

            TrainType.PUSH_UP -> {
                R.raw.push_up
            }

            TrainType.SIT_UP -> {
                R.raw.sit_up
            }

            TrainType.RUN -> {
                R.raw.squat
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .pointerInput(Unit) { // 클릭 이벤트를 무시
                    awaitPointerEventScope {
                        while (true) {
                            awaitPointerEvent()
                        }
                    }
                },
        ) {
            RiveAnimation(
                modifier = Modifier
                    .size(300.dp)
                    .pointerInput(Unit) { // 클릭 이벤트를 무시
                        awaitPointerEventScope {
                            while (true) {
                                awaitPointerEvent()
                            }
                        }
                    },
                resId = animation,
                autoplay = true,
                animationName = "Timeline 1",
                contentDescription = "Just a Rive Animation",
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 16.dp)
                .pointerInput(Unit) { // 클릭 이벤트를 무시
                    awaitPointerEventScope {
                        while (true) {
                            awaitPointerEvent()
                        }
                    }
                },

            ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Colors.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = content,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Colors.White
            )
        }
    }
}

@Composable
fun ChoiceBattleModeDialog(
    fitMode: TrainType,
    selectedOption: Int,
    onOptionSelected: (Int) -> Unit,
    onCancel: () -> Unit,
    onConfirm: (MatchType, TrainType) -> Unit,
) {
    val options = listOf("단기전" to "30초", "중기전" to "3분", "장기전" to "10분")

    AlertDialog(
        containerColor = Colors.White,
        onDismissRequest = { onCancel() },
        title = {
            Text(
                text = "운동 시간",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = Colors.LightPrimaryColorDark
            )
        },
        text = {
            Column {
                options.forEachIndexed { index, option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(
                                if (selectedOption == index) Colors.LightPrimaryColor else Colors.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                BorderStroke(1.dp, Colors.LightPrimaryColor),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .fitBattleClickable { onOptionSelected(index) }
                            .padding(vertical = 12.dp, horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = option.first,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (selectedOption == index) Colors.White else Colors.Black
                        )
                        Text(
                            text = option.second,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (selectedOption == index) Colors.White else Colors.Black
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (selectedOption == -1) return@TextButton
                    val matchType: MatchType = when (fitMode) {
                        TrainType.PUSH_UP -> {
                            when (selectedOption) {
                                0 -> MatchType.SHORTPUSHUP
                                1 -> MatchType.MIDDLEPUSHUP
                                2 -> MatchType.LONGPUSHUP
                                else -> MatchType.SHORTPUSHUP
                            }
                        }

                        TrainType.SQUAT -> {
                            when (selectedOption) {
                                0 -> MatchType.SHORTSQUAT
                                1 -> MatchType.MIDDLESQUAT
                                2 -> MatchType.LONGSQUAT
                                else -> MatchType.SHORTSQUAT
                            }
                        }

                        TrainType.SIT_UP -> {
                            when (selectedOption) {
                                0 -> MatchType.SHORTSITUP
                                1 -> MatchType.MIDDLESITUP
                                2 -> MatchType.LONGSITUP
                                else -> MatchType.SHORTSITUP
                            }
                        }

                        TrainType.RUN -> {
                            MatchType.SHORTSQUAT
                        }
                    }
                    onConfirm(matchType, fitMode)
                }
            ) {
                Text(
                    text = "한판",
                    color = if (selectedOption == -1) Colors.GrayDark else Colors.LightPrimaryColor
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(text = "취소", color = Colors.Black)
            }
        }
    )
}