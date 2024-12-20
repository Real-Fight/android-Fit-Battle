package com.qpeterp.fitbattle.presentation.features.train.screen

import android.speech.tts.TextToSpeech
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.qpeterp.fitbattle.domain.usecase.pose.TextToSpeech as TTS

@Composable
fun rememberTextToSpeech(): MutableState<TextToSpeech?> {
    val textToSpeech = TTS()
    val context = LocalContext.current
    val tts = remember { mutableStateOf<TextToSpeech?>(null) }
    DisposableEffect(context) {
        val speech = textToSpeech.textToSpeech(context, tts.value)
        speech.setSpeechRate(3.0f) // 말하기 속도 2배로 설정
        tts.value = speech

        onDispose {
            speech.stop()
            speech.shutdown()
        }
    }
    return tts
}