package com.qpeterp.fitbattle.presentation.root.navigation

sealed class NavGroup(val group: String) {
    data object Auth: NavGroup("auth") {
        const val LOGIN = "login"
        const val REGISTER_ID = "registerId"
        const val REGISTER_NAME = "registerName"
        const val REGISTER_PASSWORD = "registerPassword"
        const val REGISTER_COMPLETE = "registerComplete"
    }

    data object Main: NavGroup("main") {
        const val MAIN = "main"
        const val HOME = "home"
        const val BATTLE = "battle"
        const val RANKING = "ranking"
        const val PROFILE = "profile"
    }

    data object Feature: NavGroup("feature") {
        const val TRAIN = "train"
        const val SETTING = "setting"
    }
}