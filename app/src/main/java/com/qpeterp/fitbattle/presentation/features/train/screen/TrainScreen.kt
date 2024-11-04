@file:OptIn(ExperimentalMaterial3Api::class)

package com.qpeterp.fitbattle.presentation.features.train.screen

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.qpeterp.fitbattle.common.Constant
import com.qpeterp.fitbattle.domain.model.train.TrainType
import com.qpeterp.fitbattle.presentation.features.train.viewmodel.TrainViewModel
import com.qpeterp.fitbattle.presentation.theme.Colors
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Composable
fun TrainScreen(
    trainType: TrainType,
    viewModel: TrainViewModel
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            factory = { context ->
                val preview = PreviewView(context).apply {
                    layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
                    layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
                }
                val cameraExecutor = Executors.newSingleThreadExecutor()

//                startCamera(
//                    previewView = preview,
//                    context = context,
//                    lifecycleOwner = lifecycleOwner,
//                    cameraExecutor = cameraExecutor,
//                    viewModel = viewModel
//                )
                preview
            },
            modifier = Modifier.fillMaxSize()
        ) {}

        MyBottomSheetScreen(
            viewModel = viewModel
        )
    }

    DisposableEffect(lifecycleOwner) {
        onDispose {
//            phoneOrientationDetector.unregister()
        }
    }
}

@Composable
fun MyBottomSheetScreen(
    viewModel: TrainViewModel
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val count = viewModel.count

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Bottom Sheet Content")
            }
        },
        sheetPeekHeight = 64.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .padding(horizontal = 20.dp),
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
                text = count,
                color = Colors.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

//private fun startCamera(
//    previewView: PreviewView,
//    context: Context,
//    lifecycleOwner: LifecycleOwner,
//    cameraExecutor: ExecutorService,
//    viewModel: TrainViewModel
//) {
//    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
////    phoneOrientationDetector = PhoneOrientationDetector(context)
//
//    cameraProviderFuture.addListener({
//        val cameraProvider = cameraProviderFuture.get()
//
//        val preview = Preview.Builder().build().also {
//            it.setSurfaceProvider(previewView.surfaceProvider)
//        }
//
//        val imageAnalysis = ImageAnalysis.Builder()
//            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST) // 최신 이미지만 유지
//            .build().also {
//                it.setAnalyzer(
//                    cameraExecutor,
//                    ImageAnalyzer(viewModel, context)
//                ) // 분석기 설정
//            }
//
//        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//
//        try {
//            cameraProvider.unbindAll()
//            cameraProvider.bindToLifecycle(
//                lifecycleOwner,
//                cameraSelector,
//                preview,
//                imageAnalysis // 이미지 분석기도 함께 바인딩
//            )
//        } catch (e: Exception) {
//            Log.e(Constant.TAG,"카메라 초기화 중 에러 발생: ${e.localizedMessage}")
//        }
//
//    }, ContextCompat.getMainExecutor(context))
//}

