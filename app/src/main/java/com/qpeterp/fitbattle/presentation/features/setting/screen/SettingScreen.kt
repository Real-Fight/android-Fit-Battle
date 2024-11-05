package com.qpeterp.fitbattle.presentation.features.setting.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.qpeterp.fitbattle.application.MyApplication
import com.qpeterp.fitbattle.presentation.extensions.fitBattleClickable
import com.qpeterp.fitbattle.presentation.features.setting.viewModel.SettingViewModel
import com.qpeterp.fitbattle.presentation.theme.Colors

enum class ActionType {
    ARROW, SWITCH, NONE
}

@Composable
fun SettingScreen(
    navController: NavController,
    viewModel: SettingViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .padding(20.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "설정",
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "앱 정보",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Colors.GrayDark
            )
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Colors.White, RoundedCornerShape(12.dp))
                    .padding(20.dp)
            ) {
                SettingCard(
                    icon = Icons.Outlined.Visibility,
                    content = "개인 정보 정책",
                    actionType = ActionType.ARROW
                ) {

                }
                Spacer(modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Colors.GrayLight))
                SettingCard(
                    icon = Icons.Outlined.Info,
                    content = "앱 정보",
                    actionType = ActionType.ARROW
                ) {

                }
            }
        }

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "기능",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Colors.GrayDark
            )
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Colors.White, RoundedCornerShape(12.dp))
                    .padding(20.dp)
            ) {
                SettingCard(
                    icon = Icons.Outlined.Mic,
                    iconColor = Colors.Green,
                    iconBackgroundColor = Colors.LightGreen,
                    content = "음성 옵션(TTS)",
                    actionType = ActionType.SWITCH
                ) {

                }
            }
        }

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "계정 관리",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Colors.GrayDark
            )
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Colors.White, RoundedCornerShape(12.dp))
                    .padding(20.dp)
            ) {
                SettingCard(
                    icon = Icons.Outlined.Logout,
                    iconColor = Colors.Red,
                    iconBackgroundColor = Colors.LightRed,
                    content = "로그아웃",
                    actionType = ActionType.NONE
                ) {
                    MyApplication.prefs.clearToken()
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                }
                Spacer(modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Colors.GrayLight))
                SettingCard(
                    icon = Icons.Outlined.Delete,
                    iconColor = Colors.Red,
                    iconBackgroundColor = Colors.LightRed,
                    content = "회원 탈퇴",
                    contentColor = Colors.Red,
                    actionType = ActionType.NONE
                ) {

                }
            }
        }
    }
}

@Composable
private fun SettingCard(
    icon: ImageVector,
    iconColor: Color = Colors.Black,
    iconBackgroundColor: Color = Colors.BackgroundColor,
    content: String,
    contentColor: Color = Colors.Black,
    actionType: ActionType,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fitBattleClickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                tint = iconColor,
                contentDescription = null,
                modifier = Modifier
                    .background(iconBackgroundColor, RoundedCornerShape(8.dp))
                    .padding(12.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = content,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                color = contentColor
            )
        }

        when (actionType) {
            ActionType.ARROW -> {

            }

            ActionType.SWITCH -> {

            }

            ActionType.NONE -> {
                Box {}
            }
        }
    }
}