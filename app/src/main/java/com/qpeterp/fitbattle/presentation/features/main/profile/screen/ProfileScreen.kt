package com.qpeterp.fitbattle.presentation.features.main.profile.screen

import android.annotation.SuppressLint
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.qpeterp.fitbattle.R
import com.qpeterp.fitbattle.presentation.features.main.profile.viewmodel.ProfileViewModel
import com.qpeterp.fitbattle.presentation.theme.Colors
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.Bitmap
import com.qpeterp.fitbattle.domain.model.battle.result.GameResult
import com.qpeterp.fitbattle.domain.model.battle.type.MatchType
import com.qpeterp.fitbattle.presentation.core.component.FitBattleTextField
import com.qpeterp.fitbattle.presentation.extensions.fitBattleClickable
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getMyRankInfo()
        viewModel.getHistory()
    }
    val coroutineScope = rememberCoroutineScope()
    val isLoading = viewModel.isLoading.collectAsState().value

    if (!isLoading) {
        val historyList = viewModel.historyList.value!!
        val myProfileInfo = viewModel.myRankInfo!!
        var editStatusState by remember { mutableStateOf(false) }
        var selectedBitmap by remember { mutableStateOf<Bitmap?>(null) }

        val context = LocalContext.current

        // 이미지 선택을 위한 ActivityResultLauncher
        val imageLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                selectedBitmap = bitmap // 선택된 이미지를 상태에 저장
            }
        }

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 20.dp),
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Colors.White, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            AsyncImage(
                                model = ImageRequest
                                    .Builder(LocalContext.current)
                                    .data(myProfileInfo.profileImgUrl)
                                    .build(),
                                contentDescription = "my Profile Image",
                                contentScale = ContentScale.Crop,
                                imageLoader = ImageLoader(LocalContext.current),
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .size(86.dp)
                                    .clip(CircleShape)
                            )
                            Icon(
                                imageVector = Icons.Outlined.CameraAlt,
                                contentDescription = "icon to change profile Image",
                                tint = Colors.Black,
                                modifier = Modifier
                                    .size(24.dp)
                                    .fitBattleClickable { imageLauncher.launch("image/*") }
                            )
                        }

                        Spacer(modifier = Modifier.width(20.dp)) // 간격

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            Text(
                                text = myProfileInfo.name,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = Colors.Black
                            )
                            StatusText(
                                "순위",
                                myProfileInfo.ranking.toString()
                            )
                            StatusText(
                                "전투력",
                                myProfileInfo.totalPower.toString()
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth()
                        .background(Colors.White, RoundedCornerShape(12.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Absolute.SpaceAround,
                    ) {
                        StatusText(
                            "힘",
                            myProfileInfo.strength.toString(),
                        )
                        StatusText(
                            "체력",
                            myProfileInfo.endurance.toString(),
                        )
                        StatusText(
                            "민첩성",
                            myProfileInfo.agility.toString(),
                        )
                        StatusText(
                            "정신력",
                            myProfileInfo.willpower.toString(),
                        )
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "상태 메세지",
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = Colors.Black
                        )
                        IconButton(
                            onClick = {
                                editStatusState = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = "icon to edit status message",
                                tint = Colors.GrayDark
                            )
                        }
                    }

                    if (myProfileInfo.statusMessage.isEmpty()) {
                        Text(
                            text = "상태메세지를 입력해보세요.",
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = Colors.GrayDark,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Colors.White, RoundedCornerShape(12.dp))
                                .padding(20.dp)
                        )
                    } else {
                        Text(
                            text = myProfileInfo.statusMessage,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = Colors.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Colors.White, RoundedCornerShape(12.dp))
                                .padding(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "성공한 챌린지",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Colors.Black
                )

                Box(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth()
                        .background(Colors.White, RoundedCornerShape(12.dp))
                ) {
                    Text(
                        text = "성공한 챌린지가 없습니다.",
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        color = Colors.Black,
                        modifier = Modifier.padding(20.dp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "대전 기록",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = Colors.Black
                    )
                    Text(
                        text = "운동 ${historyList.size}판",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Colors.GrayDark
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            items(historyList) { item ->
                Box(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth()
                        .background(Colors.White, RoundedCornerShape(12.dp))
                ) {
                    BattleHistoryCard(
                        result = item.result,
                        mode = item.matchType,
                        count = item.score.toString(),
                    )
                }
            }
        }

        selectedBitmap?.let { bitmap ->
            LaunchedEffect(key1 = bitmap) {
                // patchProfile 호출 후 selectedBitmap 초기화를 마지막에 수행
                viewModel.patchProfile(bitmap)
                selectedBitmap = null
            }
        }

        if (editStatusState) {
            EditStatusDialog(
                myProfileInfo.statusMessage,
                onCancel = {
                    editStatusState = false
                },
                onConfirm = {
                    coroutineScope.launch {
                        viewModel.patchStatusMessage(it)
                        editStatusState = false // 다이얼로그 닫기
                    }
                }
            )
        }
    }
}

@Composable
private fun StatusText(
    label: String,
    status: String,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Colors.Black
        )
        Text(
            text = status,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Colors.LightPrimaryColor
        )
    }
}

@Composable
private fun BattleHistoryCard(
    result: GameResult,
    mode: MatchType,
    count: String
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_run),
                contentDescription = "Fit type image",
                modifier = Modifier.size(52.dp),
                tint = Colors.LightPrimaryColor
            )
            Column {
                Text(
                    text = result.message,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = result.resultColor
                )
                Text(
                    text = mode.length,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Colors.GrayDark
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = mode.type,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Colors.Black
            )
            Text(
                text = count,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Colors.Black
            )
        }
    }
}

@Composable
private fun EditStatusDialog(
    statusMessage: String,
    onCancel: () -> Unit,
    onConfirm: (String) -> Unit
) {
    val text = remember { mutableStateOf(statusMessage) }
    AlertDialog(
        containerColor = Colors.White,
        onDismissRequest = { onCancel() },
        title = {
            Text(
                text = "상태 메세지",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = Colors.LightPrimaryColor
            )
        },
        text = {
            FitBattleTextField(
                label = "상태 메세지",
                currentText = text.value,
                keyboardType = KeyboardType.Text
            ) {
                text.value = it
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(text.value)
                }
            ) {
                Text(
                    text = "확인",
                    color = Colors.LightPrimaryColor
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(text = "취소", color = Colors.Black)
            }
        }
    )
}