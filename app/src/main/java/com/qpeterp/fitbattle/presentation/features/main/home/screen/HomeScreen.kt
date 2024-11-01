package com.qpeterp.fitbattle.presentation.features.main.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
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
                            text = "5Km 달리기",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Colors.Black
                        )
                    }

                    TodayMissionButton {

                    }
                }
            }
        }
    }
}

@Composable
private fun TodayMissionButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(86.dp)
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
                .size(32.dp)
                .align(Alignment.Center)
        )
    }
}