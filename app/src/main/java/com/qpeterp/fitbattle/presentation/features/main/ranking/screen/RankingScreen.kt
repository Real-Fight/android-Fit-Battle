package com.qpeterp.fitbattle.presentation.features.main.ranking.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.qpeterp.fitbattle.domain.model.rank.Rank
import com.qpeterp.fitbattle.presentation.features.main.ranking.viewmodel.RankingViewModel
import com.qpeterp.fitbattle.presentation.theme.Colors
import kotlinx.coroutines.Dispatchers

@Composable
fun RankingScreen(
    navController: NavController,
    viewModel: RankingViewModel = viewModel()
) {
    val dummyDataList by viewModel.dummyDataList.collectAsState()

    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Colors.White, RoundedCornerShape(12.dp))
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally, // 수평 중앙 정렬
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data("https://mikuwallets.kr/assets/img/project/2017_lpip_header.jpg")
                    .build(),
                contentDescription = "My Profile Image",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ph_profile), // 로딩 중 보여줄 이미지
//                error = painterResource(R.drawable.error_image), // 로딩 실패 시 보여줄 이미지
                imageLoader = ImageLoader(LocalContext.current),
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)

            )
            Text(
                text = "이성은이라는 뜻",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Colors.Black
            )

            Text(
                text = "100등",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
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
            .background(Colors.White, RoundedCornerShape(12.dp))
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.ranking.toString(),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Colors.LightPrimaryColor
            )
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(item.profile)
                    .build(),
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ph_profile), // 로딩 중 보여줄 이미지
//                error = painterResource(R.drawable.error_image), // 로딩 실패 시 보여줄 이미지
                imageLoader = ImageLoader(LocalContext.current),
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)

            )
            Text(
                text = item.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Colors.Black
            )
        }

        Text(
            text = item.score.toString(),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Colors.LightPrimaryColor
        )
    }
}