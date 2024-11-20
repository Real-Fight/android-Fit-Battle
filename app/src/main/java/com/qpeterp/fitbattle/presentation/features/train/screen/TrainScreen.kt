@file:OptIn(ExperimentalMaterial3Api::class)

package com.qpeterp.fitbattle.presentation.features.train.screen

import android.app.Activity
import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.qpeterp.fitbattle.application.MyApplication
import com.qpeterp.fitbattle.common.Constant
import com.qpeterp.fitbattle.domain.model.train.TrainHistory
import com.qpeterp.fitbattle.domain.model.train.TrainType
import com.qpeterp.fitbattle.domain.usecase.pose.train.ImageAnalyzer
import com.qpeterp.fitbattle.domain.usecase.pose.PhoneOrientationDetector
import com.qpeterp.fitbattle.presentation.core.component.FitBattleDialog
import com.qpeterp.fitbattle.presentation.extensions.fitBattleClickable
import com.qpeterp.fitbattle.presentation.features.train.viewmodel.TrainViewModel
import com.qpeterp.fitbattle.presentation.theme.Colors
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private lateinit var phoneOrientationDetector: PhoneOrientationDetector

@Composable
fun TrainScreen(
    navController: NavController,
    viewModel: TrainViewModel = hiltViewModel(),
) {
    val trainType = MyApplication.prefs.trainType
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    var exitState by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val tts = rememberTextToSpeech()
    val fitState = viewModel.fitState.observeAsState()
    val angle = viewModel.angle.observeAsState()

    var ttsState by remember { mutableStateOf(PoseType.UP) }

    LifecycleStartEffect(Unit) {
        // lifecycle start 시 할것,
        val activity = context as? Activity
        activity?.window?.decorView?.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_IMMERSIVE or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )

        onStopOrDispose {
            // lifecycle stop시 할것
            activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
    }

    BackHandler(enabled = true) {
        exitState = true
    }

    LaunchedEffect(Unit) {
        when (trainType) {
            "스쿼트" -> viewModel.setTrainType(TrainType.SQUAT)
            "푸쉬업" -> viewModel.setTrainType(TrainType.PUSHUP)
            "윗몸 일으키기" -> viewModel.setTrainType(TrainType.SITUP)
        }
    }

    if (MyApplication.prefs.ttsState && fitState.value == ttsState) {
        ttsState = if (ttsState == PoseType.DOWN) PoseType.UP else PoseType.DOWN
        tts.value?.speak(
            fitState.value?.message,
            TextToSpeech.QUEUE_FLUSH,
            null,
            ""
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "icon to ArrowBack",
                    tint = Colors.Black,
                    modifier = Modifier.fitBattleClickable {
                        exitState = true
                    }
                )
                Text(
                    text = "$trainType 훈련",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = Colors.Black
                )
            }

            Box {
                AndroidView(
                    factory = { context ->
                        val preview = PreviewView(context).apply {
                            layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
                            layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
                        }
                        val cameraExecutor = Executors.newSingleThreadExecutor()

                        startCamera(
                            previewView = preview,
                            context = context,
                            lifecycleOwner = lifecycleOwner,
                            cameraExecutor = cameraExecutor,
                            viewModel = viewModel
                        )
                        preview
                    },
                    modifier = Modifier
                        .fillMaxSize()
                )

                val text = when (viewModel.trainType.value) {
                    TrainType.SQUAT -> "무릎 각도"
                    TrainType.PUSHUP -> "팔꿈치 각도"
                    TrainType.SITUP -> "올라간 정도"
                    TrainType.RUN -> ""
                    else -> "문제 발생"
                }

                Text(
                    text = "$text ${angle.value.toString()}",
                    color = Colors.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .align(Alignment.TopStart) // 원하는 위치에 텍스트를 배치합니다.
                        .padding(20.dp)
                )
            }

        }

        val trainHistory = viewModel.trainHistoryList.collectAsState().value

        if (trainHistory != null) {
            MyBottomSheetScreen(
                viewModel = viewModel,
                trainHistory = trainHistory,
                modifier = Modifier.align(Alignment.BottomCenter) // 하단에 고정
            )
        }
    }

    FitBattleDialog(
        showDialog = exitState,
        title = "훈련 종료",
        titleColor = Colors.Black,
        onConfirm = {
            exitState = false
            scope.launch {
                viewModel.saveTrain()
                navController.navigate("main") {
                    popUpTo(0)
                }
            }
        },
        onDismiss = {
            exitState = false
        },
        message = "정말 훈련을 종료하시겠습까?",
    )

    DisposableEffect(lifecycleOwner) {
        onDispose {
            phoneOrientationDetector.unregister()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomSheetScreen(
    viewModel: TrainViewModel,
    trainHistory: List<TrainHistory>,
    modifier: Modifier,
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val count = viewModel.count.observeAsState()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    BottomSheetScaffold(
        sheetContainerColor = Colors.White,
        sheetMaxWidth = screenWidth,
        scaffoldState = scaffoldState,
        sheetContent = {
            Column {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .background(Colors.White, RoundedCornerShape(100.dp))
                        .border(2.dp, Colors.LightPrimaryColor, RoundedCornerShape(100.dp))
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "횟수",
                        color = Colors.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = count.value.toString(),
                        color = Colors.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                trainHistory.fastForEach {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                    ) {
                        Text(
                            text = it.trainingType,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            color = Colors.Black
                        )
                        Text(
                            text = it.count.toString(),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            color = Colors.Black
                        )
                        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                        val date = sdf.parse(it.createdAt)  // Parse the String into a Date
                        Text(
                            text = formatTime(Timestamp(date.time)),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            color = Colors.Black
                        )
                    }

                }
            }
        },
        sheetPeekHeight = 110.dp, // sheet의 기본 노출 높이
    ) {}
}

private fun formatTime(timestamp: Timestamp): String {
    // Get the time in milliseconds from the Timestamp
    val date = Date(timestamp.time)  // Use timestamp.time, which returns the time in milliseconds
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val formattedDate = sdf.format(date)

    return formattedDate  // Prints the formatted date
}


private fun startCamera(
    previewView: PreviewView,
    context: Context,
    lifecycleOwner: LifecycleOwner,
    cameraExecutor: ExecutorService,
    viewModel: TrainViewModel,
) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
    phoneOrientationDetector = PhoneOrientationDetector(context)

    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()

        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

        val imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST) // 최신 이미지만 유지
            .build().also {
                it.setAnalyzer(
                    cameraExecutor,
                    ImageAnalyzer(viewModel, context)
                ) // 분석기 설정
            }

        val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageAnalysis // 이미지 분석기도 함께 바인딩
            )
        } catch (e: Exception) {
            Log.e(Constant.TAG, "카메라 초기화 중 에러 발생: ${e.localizedMessage}")
        }

    }, ContextCompat.getMainExecutor(context))
}
