package com.qpeterp.fitbattle.presentation.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qpeterp.fitbattle.presentation.extensions.fitBattleClickable
import com.qpeterp.fitbattle.presentation.theme.Colors

@Composable
fun FitBattleButton(
    text: String,
    onClick: () -> Unit,
    textColor: Color = Color.White, // 기본 텍스트 색
    backgroundColor: Color = Colors.LightPrimaryColor,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = textColor,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(12.dp) // 모서리 둥글게
            )
            .fitBattleClickable(onClick = onClick)
            .padding(vertical = 16.dp, horizontal = 32.dp), // 내부 여백

        textAlign = TextAlign.Center
    )
}