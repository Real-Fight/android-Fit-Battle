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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.qpeterp.fitbattle.presentation.extensions.fitBattleClickable
import com.qpeterp.fitbattle.presentation.features.main.battle.viewmodel.BattleViewModel
import com.qpeterp.fitbattle.presentation.theme.Colors

@Composable
fun BattleScreen(
    navController: NavController,
    viewModel: BattleViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    var showDialog by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(-1) }
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
            animation = "",
        ) {
            showDialog = true
        }
        BattleCard(
            title = "스쿼트",
            content = "다리 근력을 강화하고 경쟁에서 앞서가세요!",
            animation = "",
        ) {
            showDialog = true
        }

        Text(
            text = "유산소한판",
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
        )
        BattleCard(
            title = "달리기",
            content = "지금 바로 달려서 경쟁에서 승리하세요!",
            animation = "",
        ) {
            showDialog = true
        }
    }

    if (showDialog) {
        ChoiceBattleModeDialog(
            selectedOption = selectedOption,
            onOptionSelected = { selectedOption = it },
            onCancel = {
                showDialog = false
                selectedOption = -1
            },
            onConfirm = {
                // 확인 시 동작
                showDialog = false
                selectedOption = -1
            }
        )
    }
}

@Composable
private fun BattleCard(
    title: String,
    content: String,
    animation: String, // 추후 추가 예정
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
            modifier = Modifier.matchParentSize()
        ) {
            drawCircle(
                color = Colors.GrayLightTransparent, // 원 색상
                radius = 100.dp.toPx(), // 원 크기
                center = Offset(size.width * 0.8f, size.height * 0.1f) // 오른쪽 위 모서리로 원 위치 조정
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 16.dp),
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
    selectedOption: Int,
    onOptionSelected: (Int) -> Unit,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    val options = listOf("단기전" to "30초", "중기전" to "5분", "장기전" to "30분")

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
                    onConfirm()
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