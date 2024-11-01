package com.qpeterp.fitbattle.presentation.features.main.ranking.screen

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.qpeterp.fitbattle.domain.model.rank.Rank
import com.qpeterp.fitbattle.presentation.features.main.ranking.viewmodel.RankingViewModel
import com.qpeterp.fitbattle.presentation.theme.Colors

@Composable
fun RankingScreen(
    navController: NavController,
    viewModel: RankingViewModel = viewModel()
) {
    val dummyDataList by viewModel.dummyDataList.collectAsState()

    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Colors.BackgroundColor, Colors.White), // 그라데이션 색상
                        start = Offset(0f, 0f), // 상단 시작점
                        end = Offset(0f, Float.POSITIVE_INFINITY) // 하단 끝점
                    ),
                    RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                )
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AsyncImage(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data("https://mikuwallets.kr/assets/img/project/2017_lpip_header.jpg")
                        .build(),
                    contentDescription = "My Profile Image",
                    contentScale = ContentScale.Crop,
                    imageLoader = ImageLoader(LocalContext.current),
                    modifier = Modifier
                        .size(76.dp)
                        .clip(CircleShape)
                )
                Column {
                    Text(
                        text = "이성은이라는 뜻",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Colors.Black
                    )
                    Text(
                        text = "1023",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        color = Colors.LightPrimaryColor
                    )
                }
            }

            Text(
                text = "No.4",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Colors.LightPrimaryColor
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(dummyDataList) { item ->
                RankCard(item)
            }
        }
    }
}

@Composable
fun RankCard(
    item: Rank
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Colors.White, RoundedCornerShape(12.dp))
            .padding(horizontal = 20.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.ranking.toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Colors.LightPrimaryColor
            )
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(40.dp)
                    .background(color = Colors.GrayLight)
            )
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(item.profile)
                    .build(),
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                imageLoader = ImageLoader(LocalContext.current),
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
            )
            Text(
                text = item.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Colors.Black
            )
        }

        Text(
            text = item.score.toString(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Colors.LightPrimaryColor
        )
    }
}