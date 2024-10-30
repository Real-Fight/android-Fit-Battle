package com.qpeterp.fitbattle.presentation.features.auth.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    fun login(username: String, password: String, onLoginSuccess: () -> Unit, onLoginFailure: (String) -> Unit) {
        viewModelScope.launch {
            // 로그인 로직 구현 (예: API 호출)
            if (username == "test" && password == "password") {
                onLoginSuccess()
            } else {
                onLoginFailure("잘못된 정보가 있습니다. 다시 한번 확인해주세요.")
            }
        }
    }
}