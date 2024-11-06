package com.qpeterp.fitbattle.presentation.core.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qpeterp.fitbattle.presentation.extensions.fitBattleClickable
import com.qpeterp.fitbattle.presentation.theme.Colors

@Composable
fun FitBattleDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    titleColor: Color = Colors.Black,
    message: String,
    confirmColor: Color = Colors.LightPrimaryColor,
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            containerColor = Colors.White,
            titleContentColor = Colors.LightPrimaryColor,
            shape = RoundedCornerShape(12.dp),
            title = {
                Text(
                    text = title,
                    color = titleColor
                )
            },
            text = { Text(text = message) },
            confirmButton = {
                Text(
                    text = "확인",
                    color = confirmColor,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fitBattleClickable {
                            onConfirm()
                        }
                        .padding(horizontal = 8.dp)
                )
            },
            dismissButton = {
                Text(
                    text = "취소",
                    color = Colors.Black,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fitBattleClickable {
                            onDismiss()
                        }
                        .padding(horizontal = 8.dp)
                )
            }
        )
    }
}