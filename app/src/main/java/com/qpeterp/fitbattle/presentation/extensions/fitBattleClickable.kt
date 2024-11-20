package com.qpeterp.fitbattle.presentation.extensions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

/**
 * @param onClick action when click, active lambda fun(if null non click).
 */
@Composable
fun Modifier.fitBattleClickable(
    onClick: () -> Unit
): Modifier {
    val interactionSource = remember { MutableInteractionSource() }

    return this.clickable(
        onClick = onClick,
        indication = null,  // ripple 효과 제거
        interactionSource = interactionSource
    )
}