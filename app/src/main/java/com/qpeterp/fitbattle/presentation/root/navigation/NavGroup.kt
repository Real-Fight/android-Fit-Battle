package com.qpeterp.fitbattle.presentation.root.navigation

sealed class NavGroup {
    data object Auth: NavGroup() {
        const val LOGIN = "login"
        const val REGISTER_ID = "registerId"
        const val REGISTER_NAME = "registerName"
        const val REGISTER_PASSWORD = "registerPassword"
        const val REGISTER_COMPLETE = "registerComplete"
    }

    data object Main: NavGroup() {
        const val MAIN = "main"
        const val HOME = "home"
        const val BATTLE = "battle"
        const val RANKING = "ranking"
        const val PROFILE = "profile"
    }

    data object Feature: NavGroup() {
        const val TRAIN = "train"
        const val SETTING = "setting"
        const val LOADING = "loading"
        const val MUSCLE_BATTLE = "muscleBattle"
    }
}