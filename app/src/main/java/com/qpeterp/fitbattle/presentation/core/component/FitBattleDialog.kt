package com.qpeterp.fitbattle.presentation.core.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    message: String,
    isPositive: Boolean = true
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            containerColor = Colors.White,
            titleContentColor = Colors.LightPrimaryColor,
            shape = RoundedCornerShape(12.dp),
            title = { Text(text = title) },
            text = { Text(text = message) },
            confirmButton = {
                Text(
                    text = "확인",
                    color = if (isPositive) Colors.LightPrimaryColor else Colors.Black,
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
                    color = if (isPositive) Colors.Black else Colors.LightPrimaryColor,
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