package com.qpeterp.fitbattle.presentation.features.auth.register.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.qpeterp.fitbattle.presentation.core.component.FitBattleButton
import com.qpeterp.fitbattle.presentation.core.component.FitBattleTextField
import com.qpeterp.fitbattle.presentation.features.auth.register.viewmodel.RegisterViewModel
import com.qpeterp.fitbattle.presentation.theme.Colors

@Composable
fun RegisterCompleteScreen(
    navController: NavController,
) {
    BackHandler(enabled = true) {}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.White)
            .padding(horizontal = 20.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "좋습니다!",
                color = Colors.LightPrimaryColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                "이제 운동 한판하러 가볼까요?",
                color = Colors.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(top = 10.dp)
            )
        }

        Column {
            FitBattleButton(
                text = "로그인",
                onClick = {
                    navController.navigate("login")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}