package com.qpeterp.fitbattle.domain.model.battle.result

import androidx.compose.ui.graphics.Color
import com.qpeterp.fitbattle.presentation.theme.Colors

enum class GameResult(
    val message: String,
    val resultColor: Color
) {
    WIN("승리", Colors.Blue),
    LOSE("패배", Colors.Red),
    DRAW("무승부", Colors.GrayDark)
}