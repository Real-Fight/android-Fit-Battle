package com.qpeterp.fitbattle.presentation.features.main.battle.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.qpeterp.fitbattle.common.Constant
import com.qpeterp.fitbattle.data.socket.data.MatchType
import com.qpeterp.fitbattle.domain.model.train.TrainType
import com.qpeterp.fitbattle.presentation.features.battle.common.BattleConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BattleViewModel @Inject constructor(): ViewModel() {
    fun setMatchType(matchType: MatchType, fitType: TrainType) {
        Log.d(Constant.TAG, "matchType is $matchType")
        BattleConstants.MATCH_TYPE = matchType
        BattleConstants.FIT_TYPE = fitType
    }
}