package com.qpeterp.fitbattle.presentation.features.main.profile.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.qpeterp.fitbattle.R
import com.qpeterp.fitbattle.presentation.features.main.profile.viewmodel.ProfileViewModel
import com.qpeterp.fitbattle.presentation.theme.Colors
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = viewModel()
) {
    val dummyList by viewModel.dummyDataList.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .scrollable(
                state = scrollState,
                orientation = Orientation.Vertical
            )
    ) {
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
                AsyncImage(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data("https://img.freepik.com/free-photo/spectrum-flashes-coloured-light_23-2151792416.jpg")
                        .build(),
                    contentDescription = "my Profile Image",
                    contentScale = ContentScale.Crop,
                    imageLoader = ImageLoader(LocalContext.current),
                    modifier = Modifier
                        .size(86.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(20.dp)) // 간격

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(
                        text = "이성은이라는 뜻",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Colors.Black
                    )
                    StatusText(
                        "순위",
                        "100"
                    )
                    StatusText(
                        "전투력",
                        "1243"
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
                    "123",
                )
                StatusText(
                    "체력",
                    "123",
                )
                StatusText(
                    "민첩성",
                    "123",
                )
                StatusText(
                    "정신력",
                    "123",
                )
            }
        }

        Text(
            text = "성공한 챌린지",
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
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
                fontSize = 20.sp,
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
                fontSize = 20.sp,
                color = Colors.Black
            )
            Text(
                text = "운동 ${dummyList.size}판",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = Colors.GrayDark
            )
        }

        LazyColumn(
            modifier = Modifier.padding(top = 10.dp)
        ) {
            items(dummyList) { item ->
                Box(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth()
                        .background(Colors.White, RoundedCornerShape(12.dp))
                ) {
                    BattleHistoryCard(
                        result = item.result,
                        mode = item.mode,
                        count = item.count,
                    )
                }
            }
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
    result: Boolean,
    mode: String,
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
                    text = if (result) "승리" else "패배",
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = if (result) Colors.Blue else Colors.Red
                )
                Text(
                    text = mode,
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
                text = "스쿼트",
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