package com.qpeterp.fitbattle.presentation.features.main.rank.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.qpeterp.fitbattle.domain.model.rank.Rank
import com.qpeterp.fitbattle.presentation.extensions.shimmerEffect
import com.qpeterp.fitbattle.presentation.features.main.rank.viewmodel.RankingViewModel
import com.qpeterp.fitbattle.presentation.theme.Colors

@Composable
fun RankingScreen(
    navController: NavController,
    viewModel: RankingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    // 화면 로드 시 데이터 불러오기
    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    if (uiState.isLoading) {
        LoadingScreen()
    } else {
        RankingContent(
            rankingList = uiState.rankingList,
            myProfileInfo = uiState.myRankInfo
        )
    }
}

@Composable
fun RankingContent(
    rankingList: List<Rank>,
    myProfileInfo: Rank,
) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Colors.BackgroundColor, Colors.White),
                        start = Offset(0f, 0f),
                        end = Offset(0f, Float.POSITIVE_INFINITY)
                    ),
                    RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                )
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 프로필 정보
            AsyncImage(
                model = myProfileInfo.profileImgUrl,
                contentDescription = "My Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(76.dp)
                    .clip(CircleShape)
            )
            Column {
                Text(
                    text = myProfileInfo.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Colors.Black
                )
                Text(
                    text = myProfileInfo.totalPower.toString(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    color = Colors.LightPrimaryColor
                )
            }
            Text(
                text = "No.${myProfileInfo.ranking}",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Colors.LightPrimaryColor
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(rankingList) { item ->
                RankCard(item)
            }
        }
    }
}


@Composable
fun RankCard(
    item: Rank,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Colors.White, RoundedCornerShape(12.dp))
            .padding(horizontal = 20.dp, vertical = 12.dp),
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
                    .data(item.profileImgUrl)
                    .build(),
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                imageLoader = ImageLoader(LocalContext.current),
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
            )
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = item.name,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    color = Colors.Black
                )
                Text(
                    text = item.statusMessage.ifEmpty { "..." },
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Colors.GrayDark,
                    modifier = Modifier
                        .width(170.dp)
                        .basicMarquee()
                )
            }
        }

        Text(
            text = item.totalPower.toString(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Colors.LightPrimaryColor
        )
    }
}

@Composable
fun ShimmerRankCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Colors.White, RoundedCornerShape(12.dp))
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(10.dp)
                    .height(24.dp)
                    .background(color = Colors.GrayLight)
                    .shimmerEffect(4.dp)
            )
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(40.dp)
                    .background(color = Colors.GrayLight)
                    .shimmerEffect(4.dp)
            )
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .shimmerEffect(100.dp)
            )
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(16.dp)
                        .background(color = Colors.GrayLight)
                        .shimmerEffect(4.dp)
                )
                Box(
                    modifier = Modifier
                        .width(48.dp)
                        .height(16.dp)
                        .background(color = Colors.GrayLight)
                        .shimmerEffect(4.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .width(32.dp)
                .height(40.dp)
                .background(color = Colors.GrayLight)
                .shimmerEffect(4.dp)
        )
    }
}

@Composable
fun LoadingScreen() {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Colors.BackgroundColor, Colors.White),
                        start = Offset(0f, 0f),
                        end = Offset(0f, Float.POSITIVE_INFINITY)
                    ),
                    RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                )
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 프로필 정보
            Box(
                modifier = Modifier
                    .size(76.dp)
                    .clip(CircleShape)
                    .shimmerEffect(100.dp)
            )
            Column {
                Box(
                    modifier = Modifier
                        .width(48.dp)
                        .height(16.dp)
                        .shimmerEffect(4.dp),
                )
                Box(
                    modifier = Modifier
                        .width(64.dp)
                        .height(16.dp)
                        .shimmerEffect(4.dp),
                )
            }
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .shimmerEffect(4.dp),
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ShimmerRankCard()
            ShimmerRankCard()
            ShimmerRankCard()
            ShimmerRankCard()
            ShimmerRankCard()
            ShimmerRankCard()
            ShimmerRankCard()
            ShimmerRankCard()
            ShimmerRankCard()
            ShimmerRankCard()
            ShimmerRankCard()
        }
    }
}
