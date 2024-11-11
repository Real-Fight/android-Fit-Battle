package com.qpeterp.fitbattle.presentation.features.battle.common

import com.qpeterp.fitbattle.data.socket.data.MatchType
import com.qpeterp.fitbattle.domain.model.battle.user.User
import com.qpeterp.fitbattle.domain.model.train.TrainType

object BattleConstants {
    var ROOM_ID: String = ""
    var RIVAL_STATUS: User = User("","","", "")
    var USER_STATUS: User = User("","","", "")
    var MATCH_TYPE: MatchType = MatchType.SHORTSQUAT
    var FIT_TYPE: TrainType = TrainType.SQUAT
}