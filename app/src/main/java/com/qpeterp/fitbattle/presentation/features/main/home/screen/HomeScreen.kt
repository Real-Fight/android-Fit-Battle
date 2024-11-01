package com.qpeterp.fitbattle.presentation.features.main.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.qpeterp.fitbattle.R
import com.qpeterp.fitbattle.presentation.extensions.fitBattleClickable
import com.qpeterp.fitbattle.presentation.features.main.home.viewmodel.HomeViewModel
import com.qpeterp.fitbattle.presentation.theme.Colors

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel()
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .scrollable(
                state = scrollState,
                orientation = Orientation.Vertical
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .background(Colors.White, RoundedCornerShape(12.dp))
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .padding(top = 16.dp),
            ) {
                Text(
                    text = "오늘의 퀘스트",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Colors.Black
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_run),
                            contentDescription = "Today mission fit type image",
                            modifier = Modifier.size(52.dp),
                            tint = Colors.LightPrimaryColor
                        )
                        Text(
                            text = "달리기 한판 승부",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Colors.Black
                        )
                    }

                    TodayMissionButton {
                        // TODO: 운동 dialog 띄우기
                    }
                }
            }
        }

        Text(
            text = "챌린지",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Colors.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        )

        Text(
            text = "훈련",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Colors.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        )

        Text(
            text = "근력",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Colors.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        )

        TrainingCard(
            icon = painterResource(R.drawable.ic_training_strength),
            title = "푸쉬업 훈련"
        ) {

        }

        TrainingCard(
            icon = painterResource(R.drawable.ic_training_strength),
            title = "스쿼트 훈련"
        ) {

        }

        Text(
            text = "체력",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Colors.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        )

        TrainingCard(
            icon = painterResource(R.drawable.ic_training_stamina),
            title = "달리기 훈련"
        ) {

        }
    }
}

@Composable
private fun TodayMissionButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(72.dp)
            .paint(
                painter = painterResource(R.drawable.bac_mission_button),
                contentScale = ContentScale.Fit,
            )
            .fitBattleClickable { onClick() }
    ) {
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "button to battle page",
            tint = Colors.White,
            modifier = Modifier
                .size(28.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun TrainingCard(
    icon: Painter,
    title: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 16.dp)
            .background(Colors.White, RoundedCornerShape(12.dp))
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = icon,
                    contentDescription = "training type icon",
                    modifier = Modifier.size(52.dp),
                    tint = Colors.LightPrimaryColor
                )
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Icon(
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = "arrow icon to Training",
                modifier = Modifier
                    .size(36.dp)
                    .fitBattleClickable { onClick() },
                tint = Colors.LightPrimaryColor
            )
        }
    }
}