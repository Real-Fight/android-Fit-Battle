package com.qpeterp.fitbattle.presentation.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.qpeterp.fitbattle.presentation.theme.Colors


@Composable
fun FitBattleTextField(
    label: String,
    currentText: String = "",
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit // 상태 변경을 처리할 콜백 추가
) {
    val text = remember { mutableStateOf(currentText) }
    val passwordVisible =
        remember { mutableStateOf(keyboardType != KeyboardType.Password) } // 비밀번호 가시성 상태

    TextField(
        value = text.value,
        onValueChange = {
            text.value = it
            onValueChange(it)
        },
        label = { Text(label, color = Colors.GrayDark) },
        modifier = Modifier
            .fillMaxWidth()
            .background(Colors.White), // 배경색 설정
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Colors.Transparent, // 포커스 시 배경색
            unfocusedContainerColor = Colors.Transparent, // 포커스 안 됐을 때 배경색
            focusedIndicatorColor = Colors.LightPrimaryColor, // 포커스 시 밑줄 색
            unfocusedIndicatorColor = Colors.Black // 포커스 안 됐을 때 밑줄 색
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = if (passwordVisible.value) KeyboardType.Text else KeyboardType.Password,
            imeAction = ImeAction.Done // 입력 완료 액션 추가
        ),
        visualTransformation = if (passwordVisible.value) {
            VisualTransformation.None // 비밀번호가 보일 때 변환 없음
        } else {
            PasswordVisualTransformation() // 비밀번호 숨김 처리
        },
        trailingIcon = if (keyboardType == KeyboardType.Password) {
            {
                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(
                        imageVector = if (passwordVisible.value) {
                            Icons.Outlined.Visibility
                        } else {
                            Icons.Outlined.VisibilityOff
                        },
                        contentDescription = if (passwordVisible.value) "Hide password" else "Show password"
                    )
                }
            }
        } else {
            null
        }
    )
}