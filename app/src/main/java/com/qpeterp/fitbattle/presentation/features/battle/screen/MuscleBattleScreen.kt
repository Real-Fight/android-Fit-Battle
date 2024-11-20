package com.qpeterp.fitbattle.presentation.features.battle.screen

import android.app.Activity
import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.qpeterp.fitbattle.R
import com.qpeterp.fitbattle.application.MyApplication
import com.qpeterp.fitbattle.common.Constant
import com.qpeterp.fitbattle.data.socket.data.GainedStatus
import com.qpeterp.fitbattle.domain.model.pose.PoseType
import com.qpeterp.fitbattle.domain.model.train.TrainType
import com.qpeterp.fitbattle.domain.usecase.pose.PhoneOrientationDetector
import com.qpeterp.fitbattle.domain.usecase.pose.battle.ImageAnalyzer
import com.qpeterp.fitbattle.presentation.core.component.FitBattleDialog
import com.qpeterp.fitbattle.presentation.extensions.fitBattleClickable
import com.qpeterp.fitbattle.presentation.features.battle.common.BattleConstants
import com.qpeterp.fitbattle.presentation.features.battle.viewmodel.MuscleBattleViewModel
import com.qpeterp.fitbattle.presentation.theme.Colors
import kotlinx.coroutines.delay
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private lateinit var phoneOrientationDetector: PhoneOrientationDetector

@Composable
fun MuscleBattleScreen(
    navController: NavController,
    viewModel: MuscleBattleViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    var giveUpDialogState by remember { mutableStateOf(false) }
    val gameResult by viewModel.gameResult.collectAsState()
    val gainedStatus = viewModel.gainedStatus.collectAsState().value
    val fitState = viewModel.fitState.observeAsState()
    var readyState by remember { mutableStateOf(false) }

    BackHandler(enabled = true) {
        giveUpDialogState = true
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    val remainTime = viewModel.remainingTime.observeAsState()
    val userInfo = viewModel.userInfo
    val rivalInfo = viewModel.rivalInfo
    val myCount = viewModel.myCount.collectAsState()
    val tts = rememberTextToSpeech()

    if (MyApplication.prefs.ttsState && fitState.value == PoseType.UP) {
        tts.value?.speak(
            (myCount.value + 1).toString(),
            TextToSpeech.QUEUE_FLUSH,
            null,
            ""
        )
    }

    val rivalCount = viewModel.rivalCount.collectAsState()

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

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 12.dp)
    ) {
        if (readyState) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(
                            Colors.GrayLight,
                            RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
                        )
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                ) {
                    Text(
                        text = "남은 시간",
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )
                    if (remainTime.value != 0) {
                        Timer(remainTime.value!!)
                    }
                }

                Icon(
                    imageVector = Icons.Outlined.Logout,
                    contentDescription = "icon to give up",
                    tint = Colors.Red,
                    modifier = Modifier
                        .size(54.dp)
                        .padding(top = 4.dp, end = 20.dp)
                        .fitBattleClickable { giveUpDialogState = true },
                )
            }

            MuscleUserProfileCard(
                profileUrl = rivalInfo.profileUrl,
                name = rivalInfo.name,
                ranking = rivalInfo.ranking,
                count = rivalCount.value
            )
            Box(
                modifier = Modifier.weight(1F)
            ) {
                val move: Int
                val basic: Int
                val vertical: Boolean
                when (BattleConstants.FIT_TYPE) {
                    TrainType.SQUAT -> {
                        move = R.drawable.item_squat_move
                        basic = R.drawable.item_squat_basic
                        vertical = true
                    }

                    TrainType.PUSH_UP -> {
                        move = R.drawable.item_push_up_move
                        basic = R.drawable.item_push_up_basic
                        vertical = false
                    }

                    TrainType.SIT_UP -> {
                        move = R.drawable.item_situp_move
                        basic = R.drawable.item_situp_basic
                        vertical = false
                    }

                    else -> {
                        move = R.drawable.item_squat_move
                        basic = R.drawable.item_squat_basic
                        vertical = true
                    }
                }
                // AndroidView를 Box 전체를 채우도록 설정
                AndroidView(
                    factory = { context ->
                        val preview = PreviewView(context).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
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

                // 아이콘을 중앙에 배치
                Icon(
                    painter = if (fitState.value == PoseType.DOWN) painterResource(basic) else painterResource(
                        move
                    ),
                    contentDescription = "exercise example image",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(420.dp) // 아이콘 크기 조정 필요시
                        .rotate(if (vertical) 0F else 90F)
                )
            }

            MuscleUserProfileCard(
                profileUrl = userInfo.profileUrl,
                name = userInfo.name,
                ranking = userInfo.ranking,
                count = myCount.value
            )
        } else {
            LottieAnimationOnce(onAnimationEnd = { readyState = true })
            viewModel.readiedGame()
        }
    }

    if (gameResult != null) {
        GameResultDialog(
            title = gameResult!!.message,
            titleColor = gameResult!!.resultColor,
            gainedStatus = gainedStatus!!,
            onDismiss = {
                return@GameResultDialog
            },
            onConfirm = {
                viewModel.clearGameResult() // 다이얼로그 닫을 때 gameResult를 null로 설정
                navController.navigate("main") {
                    popUpTo(0)
                }
            }
        )
    }

    FitBattleDialog(
        showDialog = giveUpDialogState,
        title = "중도 포기",
        titleColor = Colors.Black,
        message = "정말 싸움을 포기하시겠습니까?\n중도 포기 시, 패배 처리됩니다.",
        confirmColor = Colors.LightPrimaryColor,
        onDismiss = { giveUpDialogState = false },
        onConfirm = {
            giveUpDialogState = false
            viewModel.giveUp()
        },
    )

    DisposableEffect(lifecycleOwner) {
        onDispose {
            phoneOrientationDetector.unregister()
        }
    }
}

@Composable
fun LottieAnimationOnce(onAnimationEnd: () -> Unit) {
    // Lottie composition 로드
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("count_down.json"))

    // LottieAnimatable 생성
    val animatable = rememberLottieAnimatable()

    // 애니메이션 실행 및 완료 감지
    LaunchedEffect(composition) {
        if (composition != null) {
            animatable.animate(
                composition = composition,
                iterations = 1 // 1회만 실행
            )
            // 애니메이션 완료 후 콜백 호출
            onAnimationEnd()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Lottie 애니메이션 UI
        LottieAnimation(
            composition = composition,
            progress = animatable.progress,
            modifier = Modifier
                .size(200.dp)
                .fillMaxSize()
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun GameResultDialog(
    title: String,
    titleColor: Color,
    gainedStatus: Map<GainedStatus, Int>,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Colors.Transparent,
        titleContentColor = Colors.LightPrimaryColor,
        shape = RoundedCornerShape(12.dp),
        text = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Colors.White, RoundedCornerShape(12.dp))
                        .padding(horizontal = 20.dp, vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = title,
                        color = titleColor,
                        textAlign = TextAlign.Center,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.SemiBold,
                    )

                    gainedStatus.forEach { (status, value) ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = status.label,
                                fontWeight = FontWeight.Medium,
                                fontSize = 20.sp,
                                color = Colors.Black
                            )
                            Text(
                                text = value.toString(),
                                fontWeight = FontWeight.Medium,
                                fontSize = 20.sp,
                                color = Colors.LightPrimaryColor
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    colors = ButtonColors(
                        containerColor = Colors.Red,
                        contentColor = Colors.Red,
                        disabledContainerColor = Colors.Red,
                        disabledContentColor = Colors.Red,
                    ),
                    shape = RoundedCornerShape(12.dp),
                    onClick = {
                        onConfirm()
                    }
                ) {
                    Text(
                        text = "확인",
                        color = Colors.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
        },
        confirmButton = {},
    )
}

@Composable
private fun MuscleUserProfileCard(
    profileUrl: String,
    name: String,
    ranking: String,
    count: Int,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .background(Colors.GrayLight, RoundedCornerShape(12.dp))
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .fillMaxWidth()
    ) {
        Row {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(profileUrl)
                    .build(),
                contentDescription = "my Profile Image",
                contentScale = ContentScale.Crop,
                imageLoader = ImageLoader(LocalContext.current),
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
            )
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = name,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
                Text(
                    text = "순위 : $ranking",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }
        }

        Text(
            text = count.toString(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 36.sp
        )
    }
}

private fun startCamera(
    previewView: PreviewView,
    context: Context,
    lifecycleOwner: LifecycleOwner,
    cameraExecutor: ExecutorService,
    viewModel: MuscleBattleViewModel,
) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
    phoneOrientationDetector = PhoneOrientationDetector(context)

    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()

        val preview = androidx.camera.core.Preview.Builder().build().also {
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

@Composable
fun Timer(timeLeft: Int) {
    var leftTime by remember { mutableStateOf(timeLeft) }

    LaunchedEffect(true) {
        while (leftTime > 0) {
            delay(1000) // 1초 대기
            leftTime -= 1 // 시간 감소
        }
    }

    Text(
        text = String.format("%02d:%02d", leftTime / 60, leftTime % 60),
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold
    )
}
