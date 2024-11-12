package com.qpeterp.fitbattle.domain.usecase.pose.battle

import android.content.Context
import android.util.Log
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseLandmark
import com.qpeterp.fitbattle.common.Constant
import com.qpeterp.fitbattle.domain.model.pose.PoseType
import com.qpeterp.fitbattle.domain.model.pose.TargetPose
import com.qpeterp.fitbattle.domain.model.pose.TargetShape
import com.qpeterp.fitbattle.domain.model.train.TrainType
import com.qpeterp.fitbattle.domain.usecase.pose.PhoneOrientationDetector
import com.qpeterp.fitbattle.domain.usecase.pose.PoseMatcher
import com.qpeterp.fitbattle.presentation.features.battle.common.BattleConstants
import com.qpeterp.fitbattle.presentation.features.battle.viewmodel.MuscleBattleViewModel

class ExerciseClassification(
    private val muscleViewModel: MuscleBattleViewModel,
    context: Context
) {
    private val phoneOrientationDetector = PhoneOrientationDetector(context = context)
    private val targetSquatMovePose: TargetPose = TargetPose(
        listOf(
            TargetShape(
                PoseLandmark.LEFT_ANKLE, PoseLandmark.LEFT_KNEE, PoseLandmark.LEFT_HIP, 100.0
            ),
            TargetShape(
                PoseLandmark.RIGHT_ANKLE, PoseLandmark.RIGHT_KNEE, PoseLandmark.RIGHT_HIP, 100.0
            ),
            TargetShape(
                PoseLandmark.LEFT_KNEE, PoseLandmark.LEFT_HIP, PoseLandmark.LEFT_SHOULDER, 100.0
            ),
            TargetShape(
                PoseLandmark.RIGHT_KNEE, PoseLandmark.RIGHT_HIP, PoseLandmark.RIGHT_SHOULDER, 100.0
            ),
        )
    )
    private val targetSquatBasicPose: TargetPose = TargetPose(
        listOf(
            TargetShape(
                PoseLandmark.LEFT_ANKLE, PoseLandmark.LEFT_KNEE, PoseLandmark.LEFT_HIP, 170.0
            ),
            TargetShape(
                PoseLandmark.RIGHT_ANKLE, PoseLandmark.RIGHT_KNEE, PoseLandmark.RIGHT_HIP, 170.0
            ),
            TargetShape(
                PoseLandmark.LEFT_KNEE, PoseLandmark.LEFT_HIP, PoseLandmark.LEFT_SHOULDER, 180.0
            ),
            TargetShape(
                PoseLandmark.RIGHT_KNEE, PoseLandmark.RIGHT_HIP, PoseLandmark.RIGHT_SHOULDER, 170.0
            ),
        )
    )
    private val targetPushUpBasicPose: TargetPose = TargetPose(
        listOf(
            TargetShape(
                PoseLandmark.RIGHT_WRIST,
                PoseLandmark.RIGHT_ELBOW,
                PoseLandmark.RIGHT_SHOULDER,
                170.0
            ),
            TargetShape(
                PoseLandmark.RIGHT_SHOULDER, PoseLandmark.RIGHT_HIP, PoseLandmark.RIGHT_HEEL, 170.0
            ),
        )
    )
    private val targetPushUpMovePose: TargetPose = TargetPose(
        listOf(
            TargetShape(
                PoseLandmark.RIGHT_WRIST,
                PoseLandmark.RIGHT_ELBOW,
                PoseLandmark.RIGHT_SHOULDER,
                80.0
            ),
            TargetShape(
                PoseLandmark.RIGHT_SHOULDER, PoseLandmark.RIGHT_HIP, PoseLandmark.RIGHT_HEEL, 170.0
            ),
        )
    )
    private val poseMatcher = PoseMatcher()
    private lateinit var movePose: TargetPose
    private lateinit var basicPose: TargetPose
    private val onPoseDetected: (pose: Pose, trainType: TrainType) -> Unit = { pose, trainType ->
        when (trainType) {
            TrainType.SQUAT -> {
                movePose = targetSquatMovePose
                basicPose = targetSquatBasicPose
            }

            TrainType.PUSH_UP -> {
                movePose = targetPushUpMovePose
                basicPose = targetPushUpBasicPose
            }

            TrainType.RUN -> {

            }
        }
        val isMovePose = poseMatcher.match(pose, movePose)
        val isBasicPose = poseMatcher.match(pose, basicPose)
        when {
            isMovePose -> {
                if (muscleViewModel.fitState.value == PoseType.DOWN) {
                    muscleViewModel.setFitState(PoseType.UP)
                }
            }

            isBasicPose -> {
                if (muscleViewModel.fitState.value == PoseType.UP) {
                    muscleViewModel.addMyCount()
                    muscleViewModel.setFitState(PoseType.DOWN)
                }
            }
        }
    }

    fun classifyExercise(pose: Pose) {
        val trainType = BattleConstants.FIT_TYPE
        when (trainType) {
            TrainType.SQUAT -> {
                if (pose.getPoseLandmark(25) == null || pose.getPoseLandmark(26) == null) return
                if (pose.getPoseLandmark(25)!!.inFrameLikelihood < 0.92 || pose.getPoseLandmark(26)!!.inFrameLikelihood < 0.92) return
                if (!phoneOrientationDetector.verticalHilt) return // check phone inclination horizontal
            }

            TrainType.PUSH_UP -> {
                if (pose.getPoseLandmark(14) == null || pose.getPoseLandmark(30) == null) return
                if (pose.getPoseLandmark(14)!!.inFrameLikelihood < 0.92 || pose.getPoseLandmark(30)!!.inFrameLikelihood < 0.92) return
                if (phoneOrientationDetector.verticalHilt) return // check phone inclination vertical
            }

            else -> {
                return
            }
        }

        Log.d(Constant.TAG, "현재 검출하려하는 포즈는 ${trainType.label} 입니다.")

        onPoseDetected(pose, trainType)
    }
}