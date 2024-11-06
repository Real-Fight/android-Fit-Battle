package com.qpeterp.fitbattle.presentation.features.auth.register.screen

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.qpeterp.fitbattle.presentation.core.component.FitBattleButton
import com.qpeterp.fitbattle.presentation.core.component.FitBattleTextField
import com.qpeterp.fitbattle.presentation.features.auth.register.viewmodel.RegisterViewModel
import com.qpeterp.fitbattle.presentation.theme.Colors

@Composable
fun RegisterPasswordScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val userPassword = remember { mutableStateOf(viewModel.password) }
    val checkPassword = remember { mutableStateOf("") }
    val errorMessage = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.White)
            .padding(horizontal = 20.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Row(
                modifier = Modifier.padding(top = 80.dp)
            ) {
                Text(
                    "비밀번호",
                    color = Colors.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    "를 입력해주세요.",
                    color = Colors.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Text(
                "비밀번호는 잊지 않도록 조심해주세요!",
                color = Colors.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(top = 10.dp)
            )

            Spacer(modifier = Modifier.height(64.dp))

            Column {
                FitBattleTextField(
                    label = "비밀번호",
                    currentText = userPassword.value,
                    keyboardType = KeyboardType.Password,
                    onValueChange = { userPassword.value = it } // 상태 변경 처리
                )

                Spacer(modifier = Modifier.height(32.dp))

                FitBattleTextField(
                    label = "비밀번호 확인",
                    keyboardType = KeyboardType.Password,
                    onValueChange = { checkPassword.value = it } // 상태 변경 처리
                )

                if (errorMessage.value.isNotEmpty()) {
                    Text(
                        text = errorMessage.value,
                        color = Colors.Red,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            }
        }

        Column {
            FitBattleButton(
                text = "다음으로",
                onClick = {
                    if (userPassword.value.isEmpty()) {
                        errorMessage.value = "비밀번호를 입력해주세요."
                        return@FitBattleButton
                    }
                    if (checkPassword.value != userPassword.value) {
                        errorMessage.value = "비밀번호가 일치하지 않습니다."
                        return@FitBattleButton
                    }
                    viewModel.inputPassword(userPassword.value)

                    navController.navigate("registerName")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}