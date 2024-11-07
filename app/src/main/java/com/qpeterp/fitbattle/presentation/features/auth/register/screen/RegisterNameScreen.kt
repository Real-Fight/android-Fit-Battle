package com.qpeterp.fitbattle.presentation.features.auth.register.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.qpeterp.fitbattle.common.Constant
import com.qpeterp.fitbattle.presentation.core.component.FitBattleButton
import com.qpeterp.fitbattle.presentation.core.component.FitBattleTextField
import com.qpeterp.fitbattle.presentation.extensions.shortToast
import com.qpeterp.fitbattle.presentation.features.auth.register.viewmodel.RegisterViewModel
import com.qpeterp.fitbattle.presentation.theme.Colors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun RegisterNameScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val userName = remember { mutableStateOf(viewModel.name) }
    val errorMessage = remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.White)
            .padding(horizontal = 20.dp, vertical = 64.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Row(
                modifier = Modifier.padding(top = 80.dp)
            ) {
                Text(
                    "이름",
                    color = Colors.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    "을 입력해주세요.",
                    color = Colors.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Text(
                "다른 사용자에게 보여질 이름이에요!\n이름 중복 안돼요.",
                color = Colors.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(top = 10.dp)
            )

            Spacer(modifier = Modifier.height(64.dp))

            Column {
                FitBattleTextField(
                    label = "이름",
                    currentText = userName.value,
                    keyboardType = KeyboardType.Text,
                    onValueChange = { userName.value = it } // 상태 변경 처리
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
                text = "회원가입",
                onClick = {
                    if (userName.value.isEmpty()) {
                        errorMessage.value = "이름을 입력해주세요."
                        return@FitBattleButton
                    }
                    viewModel.inputName(userName.value)
                    CoroutineScope(Dispatchers.Main).launch {
                        viewModel.register(
                            onRegisterSuccess = {
                                navController.navigate("registerComplete") {
                                    Log.d(Constant.TAG, "회원가입 성공 성공")
                                    popUpTo("registerName") { inclusive = true }
                                }
                            },
                            onRegisterFailure = { message ->
                                context.shortToast(message)
                            }
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}