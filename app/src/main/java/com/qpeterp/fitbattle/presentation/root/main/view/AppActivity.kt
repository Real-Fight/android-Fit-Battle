package com.qpeterp.fitbattle.presentation.root.main.view

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.qpeterp.fitbattle.application.MyApplication
import com.qpeterp.fitbattle.application.PreferenceManager
import com.qpeterp.fitbattle.presentation.core.component.FitBattleDialog
import com.qpeterp.fitbattle.presentation.root.navigation.NavigationGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppActivity : ComponentActivity() {
    private var showPermissionDialog = false
    private val multiplePermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        var allPermissionsGranted = true // 모든 권한 승인 여부를 저장하는 변수

        permissions.entries.forEach { (permission, isGranted) ->
            when {
                isGranted -> {
                    // 권한이 승인된 경우 처리할 작업
                    Log.d("Permissions", "$permission granted.")
                }

                !isGranted -> {
                    // 권한이 거부된 경우 처리할 작업
                    allPermissionsGranted = false // 하나라도 거부되면 false로 설정
                    Log.d("Permissions", "$permission denied.")
                }

                else -> {
                    // 사용자가 "다시 묻지 않음"을 선택한 경우 처리할 작업
                    Log.d("Permissions", "$permission denied with 'Don't ask again'.")
                }
            }
        }

        if (!allPermissionsGranted) {
            // TODO: 뭐 하기
            showPermissionDialog = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestPermissions()

        MyApplication.prefs = PreferenceManager(application)
        val prefs = MyApplication.prefs

        val isLoggedIn = prefs.token.isNotEmpty()

        setContent {
            MyApp(isLoggedIn)
        }
    }

    private fun requestPermissions() {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_MEDIA_IMAGES // 갤러리 접근 권한 (API 33 이상)
            )
        } else {
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_EXTERNAL_STORAGE // 갤러리 접근 권한 (API 32 이하)
            )
        }
        multiplePermissionsLauncher.launch(permissions)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyApp(
        isUserLoggedIn: Boolean
    ) {
        val navController = rememberNavController()
        NavigationGraph(
            navController = navController,
            isUserLoggedIn = isUserLoggedIn
        )
    }
}
