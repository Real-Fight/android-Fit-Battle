package com.qpeterp.fitbattle.presentation.features.main.profile.screen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.qpeterp.fitbattle.domain.model.battle.history.BattleHistory
import com.qpeterp.fitbattle.domain.model.battle.result.GameResult
import com.qpeterp.fitbattle.domain.model.battle.type.MatchType
import com.qpeterp.fitbattle.domain.model.user.User
import com.qpeterp.fitbattle.presentation.core.component.FitBattleTextField
import com.qpeterp.fitbattle.presentation.extensions.fitBattleClickable
import com.qpeterp.fitbattle.presentation.extensions.shimmerEffect
import java.io.ByteArrayOutputStream

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    // uiState 상태값을 observe
    val uiState by viewModel.uiState.collectAsState()

    // 화면 로드 시 데이터 불러오기
    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    // 로딩 중일 때
    if (uiState.isLoading) {
        ShimmerProfileContent()
    } else {
        // 로딩 완료 후 프로필 내용 화면
        ProfileContent(
            myProfileInfo = uiState.myRankInfo,
            historyList = uiState.historyList,
            onEditStatus = { newStatus ->
                viewModel.patchStatusMessage(newStatus)
            },
            onChangeProfileImage = { bitmap ->
                viewModel.patchProfile(bitmap)
            }
        )
    }
}

@Composable
fun ProfileContent(
    myProfileInfo: User?,
    historyList: List<BattleHistory>?,
    onEditStatus: (String) -> Unit,
    onChangeProfileImage: (Bitmap) -> Unit,
) {
    if (myProfileInfo == null || historyList == null) {
        // 데이터가 없을 경우 처리
        return
    }

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
                            "총합 잠재력",
                            myProfileInfo.totalPower.toString()
                        )
                    }
                }
            }

            // 나머지 프로필 내용들 (힘, 체력 등)
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

        // 대전 기록 항목들
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
        LaunchedEffect(Unit) {
            // 이미지 크기 조절 후 전송
            val resizedBitmap = resizeAndCompressBitmap(bitmap, maxWidth = 500, maxHeight = 500)
            onChangeProfileImage(resizedBitmap)
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
                onEditStatus(it)
                editStatusState = false // 다이얼로그 닫기
            }
        )
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
    count: String,
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


fun resizeAndCompressBitmap(
    bitmap: Bitmap,
    maxWidth: Int,
    maxHeight: Int,
    maxSizeInBytes: Int = 10 * 1024 * 1024,
    quality: Int = 80,
): Bitmap {
    // 비율을 유지하면서 이미지 크기 조정
    val aspectRatio = bitmap.width.toFloat() / bitmap.height.toFloat()
    val (newWidth, newHeight) = if (aspectRatio > 1) {
        maxWidth to (maxWidth / aspectRatio).toInt()
    } else {
        (maxHeight * aspectRatio).toInt() to maxHeight
    }

    val resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)

    // 압축률을 조정하여 이미지 크기 맞추기
    val byteArrayOutputStream = ByteArrayOutputStream()
    var currentQuality = quality
    do {
        byteArrayOutputStream.reset()  // 이전 출력 스트림 초기화
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, currentQuality, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        if (byteArray.size <= maxSizeInBytes) {
            // 압축된 바이트 배열을 다시 Bitmap으로 변환하여 반환
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }
        currentQuality -= 5  // 압축 품질을 낮춰가며 재시도
    } while (currentQuality > 10)  // 품질이 너무 낮아지지 않도록 최소 품질 설정

    // 10MB 이하로 압축된 최종 Bitmap 반환
    return BitmapFactory.decodeByteArray(
        byteArrayOutputStream.toByteArray(),
        0,
        byteArrayOutputStream.size()
    )
}

@Composable
private fun EditStatusDialog(
    statusMessage: String,
    onCancel: () -> Unit,
    onConfirm: (String) -> Unit,
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

@Composable
fun ShimmerProfileContent() {
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
                        // 프로필 이미지 Shimmer
                        Box(
                            modifier = Modifier
                                .size(86.dp)
                                .clip(CircleShape)
                                .background(Colors.GrayLight)
                                .shimmerEffect(100.dp)  // 프로필 이미지에 100.dp 주기
                        )

                        // 카메라 아이콘 Shimmer
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(Colors.GrayLight)
                                .shimmerEffect(4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(20.dp)) // 간격

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        // 이름 텍스트 Shimmer
                        Box(
                            modifier = Modifier
                                .width(80.dp)
                                .height(16.dp)
                                .background(Colors.GrayLight)
                                .shimmerEffect(4.dp)
                        )

                        // 순위 및 총합 잠재력 Shimmer
                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .height(14.dp)
                                .background(Colors.GrayLight)
                                .shimmerEffect(4.dp)
                        )

                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .height(14.dp)
                                .background(Colors.GrayLight)
                                .shimmerEffect(4.dp)
                        )
                    }
                }
            }

            // 나머지 프로필 내용들 (힘, 체력 등)
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
                    // 힘, 체력, 민첩성, 정신력 부분에 대한 Shimmer
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(16.dp)
                            .background(Colors.GrayLight)
                            .shimmerEffect(4.dp)
                    )
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(16.dp)
                            .background(Colors.GrayLight)
                            .shimmerEffect(4.dp)
                    )
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(16.dp)
                            .background(Colors.GrayLight)
                            .shimmerEffect(4.dp)
                    )
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(16.dp)
                            .background(Colors.GrayLight)
                            .shimmerEffect(4.dp)
                    )
                }
            }
        }

        item {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // 상태 메세지 부분 Shimmer
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(16.dp)
                            .background(Colors.GrayLight)
                            .shimmerEffect(4.dp)  // "상태 메세지" 텍스트 Shimmer
                    )

                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(Colors.GrayLight)
                            .shimmerEffect(4.dp)  // 아이콘 버튼 Shimmer
                    )
                }

                Spacer(modifier = Modifier.height(12.dp)) // 간격

                // 상태 메시지 내용 Shimmer
                Box(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(Colors.GrayLight)
                        .shimmerEffect(4.dp)  // 상태 메시지 내용 Shimmer
                )

                Spacer(modifier = Modifier.height(16.dp)) // 간격

                // 성공한 챌린지 제목 Shimmer
                Box(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                        .height(16.dp)
                        .background(Colors.GrayLight)
                        .shimmerEffect(4.dp)  // 성공한 챌린지 제목 Shimmer
                )

                Spacer(modifier = Modifier.height(10.dp)) // 간격

                // 성공한 챌린지 내용 Shimmer
                Box(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                        .height(18.dp)
                        .background(Colors.GrayLight)
                        .shimmerEffect(4.dp)  // 성공한 챌린지 내용 Shimmer
                )

                Spacer(modifier = Modifier.height(16.dp)) // 간격

                // 대전 기록 제목 Shimmer
                Box(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                        .height(16.dp)
                        .background(Colors.GrayLight)
                        .shimmerEffect(4.dp)  // 대전 기록 제목 Shimmer
                )
            }
        }

        items(4) {
            Box(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth()
                    .background(Colors.White, RoundedCornerShape(12.dp))
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
                        // 아이콘 Shimmer
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .background(color = Colors.GrayLight)
                                .shimmerEffect(4.dp)  // Corner radius set to 4.dp
                        )

                        // 텍스트 Shimmer
                        Column {
                            Box(
                                modifier = Modifier
                                    .width(120.dp)
                                    .height(16.dp)
                                    .background(color = Colors.GrayLight)
                                    .shimmerEffect(4.dp) // 메시지 부분 Shimmer
                            )

                            Box(
                                modifier = Modifier
                                    .width(80.dp)
                                    .height(14.dp)
                                    .background(color = Colors.GrayLight)
                                    .shimmerEffect(4.dp) // 모드 길이 부분 Shimmer
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // 모드 텍스트 Shimmer
                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .height(16.dp)
                                .background(color = Colors.GrayLight)
                                .shimmerEffect(4.dp)
                        )

                        // 점수 텍스트 Shimmer
                        Box(
                            modifier = Modifier
                                .width(40.dp)
                                .height(16.dp)
                                .background(color = Colors.GrayLight)
                                .shimmerEffect(4.dp)
                        )
                    }
                }
            }
        }

    }
}