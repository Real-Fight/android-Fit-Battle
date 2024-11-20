package com.qpeterp.fitbattle.presentation.features.main.home.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.qpeterp.fitbattle.R
import com.qpeterp.fitbattle.application.MyApplication
import com.qpeterp.fitbattle.domain.model.train.TrainType
import com.qpeterp.fitbattle.domain.model.user.QuestType
import com.qpeterp.fitbattle.presentation.extensions.fitBattleClickable
import com.qpeterp.fitbattle.presentation.features.main.home.viewmodel.HomeViewModel
import com.qpeterp.fitbattle.presentation.theme.Colors

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val screenScrollState = rememberScrollState()
    val challengeScrollState = rememberScrollState()
    val isLoading = viewModel.isLoading.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getQuest()
    }

    if (!isLoading) {
        val quest = viewModel.quest.value!!

        val questTypeIcon = when (quest.questType) {
            QuestType.MATCH -> painterResource(R.drawable.ic_strength)
            QuestType.SQUAT -> painterResource(R.drawable.ic_muclse)
            QuestType.PUSHUP -> painterResource(R.drawable.ic_muclse)
            QuestType.SITUP -> painterResource(R.drawable.ic_muclse)
            QuestType.WIN -> painterResource(R.drawable.ic_strength)
            QuestType.SITUPPERONEGAME -> painterResource(R.drawable.ic_muclse)
            QuestType.SQUATPERONEGAME -> painterResource(R.drawable.ic_muclse)
            QuestType.PUSHUPPERONEGAME -> painterResource(R.drawable.ic_muclse)
        }
        Column(
            modifier = Modifier
                .verticalScroll(
                    state = screenScrollState,
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .background(Colors.White, RoundedCornerShape(12.dp))
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 18.dp)
                        .padding(top = 16.dp),
                ) {
                    Text(
                        text = "오늘의 퀘스트",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Colors.Black
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = questTypeIcon,
                                contentDescription = "Today mission fit type image",
                                modifier = Modifier.size(52.dp),
                                tint = Colors.LightPrimaryColor
                            )
                            Text(
                                text = quest.message,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Colors.Black
                            )
                        }

                        TodayMissionButton(
                            isCompleted = quest.completed
                        ) {
                            // TODO: 운동 dialog 띄우기
                        }
                    }
                }
            }

            Text(
                text = "챌린지",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Colors.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .horizontalScroll(
                        state = challengeScrollState,
                    )
                    .padding(horizontal = 20.dp)
            ) {
                ChallengeCard(
                    mainColor = Colors.LightPrimaryColor,
                    subColor = Colors.LightPrimaryColorDark,
                    title = "개발자를\n이겨라!",
                    eventDate = "챌린지 기간 : 11월 30일 ~ 12월 30일",
                    content = "개발자의 기록을\n" +
                            "뛰어넘어,\n" +
                            "강함을 증명하세요!"
                ) {

                }
                ChallengeCard(
                    mainColor = Color(0xFFF0B343),
                    subColor = Color(0xFFBE7F00),
                    title = "운동의 신",
                    eventDate = "챌린지 기간 : 11월 20일 ~ 12월 12일",
                    content = "기간 내에\n" +
                            "누적 운동 횟수\n" +
                            "1500회에 도전하세요!"
                ) {
                    // Open the URL when the text is clicked
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://excessive-cashew-b68.notion.site/11-13d20279f6ea805a86daee7904abb48f?pvs=73"))
                    context.startActivity(intent)
                }
            }

            Text(
                text = "훈련",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Colors.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )

            Column {
                Text(
                    text = "근력",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Colors.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 16.dp)
                )

                TrainingCard(
                    icon = painterResource(R.drawable.ic_muclse),
                    title = "푸쉬업 훈련"
                ) {
                    MyApplication.prefs.trainType = TrainType.PUSH_UP.label
                    navController.navigate("train")
                }

                TrainingCard(
                    icon = painterResource(R.drawable.ic_muclse),
                    title = "스쿼트 훈련"
                ) {
                    MyApplication.prefs.trainType = TrainType.SQUAT.label
                    navController.navigate("train")
                }

                TrainingCard(
                    icon = painterResource(R.drawable.ic_muclse),
                    title = "윗몸 일으키기 훈련"
                ) {
                    MyApplication.prefs.trainType = TrainType.SIT_UP.label
                    navController.navigate("train")
                }

//                Text(
//                    text = "체력",
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.Medium,
//                    color = Colors.Black,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 24.dp)
//                        .padding(bottom = 16.dp)
//                )
//
//                TrainingCard(
//                    icon = painterResource(R.drawable.ic_stamina),
//                    title = "달리기 훈련"
//                ) {
//
//                }
            }
        }
    }
}

@Composable
private fun TodayMissionButton(
    isCompleted: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(72.dp)
            .paint(
                painter = if (isCompleted) painterResource(R.drawable.bac_mission_complete) else painterResource(
                    R.drawable.bac_mission_button
                ),
                contentScale = ContentScale.Fit,
            )
            .clickable { onClick() }
    ) {
        if (!isCompleted) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "button to battle page",
                tint = Colors.White,
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun ChallengeCard(
    mainColor: Color,
    subColor: Color,
    title: String,
    eventDate: String,
    textColor: Color = Colors.White,
    buttonColor: Color = Colors.White,
    content: String, // 추후, 업데이트 예정
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .width(320.dp)
            .height(240.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(mainColor, subColor),
                    start = Offset(0f, 0f),
                    end = Offset.Infinite // 끝 지점을 설정 (예: 무한 방향으로 확장)
                ), RoundedCornerShape(12.dp)
            )
            .padding(20.dp)
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
            modifier = Modifier.align(Alignment.TopStart)
        )

        Text(
            text = content,
            textAlign = TextAlign.End,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = textColor,
            modifier = Modifier.align(Alignment.TopEnd)
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = eventDate,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = textColor,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = { onClick() },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(100.dp), // 모서리를 둥글게 설정
                colors = ButtonDefaults.buttonColors(
                    contentColor = buttonColor,
                    containerColor = buttonColor
                )
            ) {
                Text(
                    text = "시작",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = mainColor
                )
            }
        }
    }
}

@Composable
private fun TrainingCard(
    icon: Painter,
    title: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 16.dp)
            .background(Colors.White, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .fitBattleClickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = icon,
                    contentDescription = "training type icon",
                    modifier = Modifier.size(52.dp),
                    tint = Colors.LightPrimaryColor
                )
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Icon(
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = "arrow icon to Training",
                modifier = Modifier.size(36.dp),
                tint = Colors.LightPrimaryColor
            )
        }
    }
}