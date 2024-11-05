package com.qpeterp.fitbattle.presentation.features.auth.login.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.qpeterp.fitbattle.application.MyApplication
import com.qpeterp.fitbattle.common.Constant
import com.qpeterp.fitbattle.common.HeaderUtil
import com.qpeterp.fitbattle.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    suspend fun login(
        loginId: String,
        password: String,
        onLoginSuccess: () -> Unit,
        onLoginFailure: (String) -> Unit
    ) {
        val result = loginUseCase(
            param = LoginUseCase.Param(
                loginId = loginId,
                password = password
            )
        )

        result.onSuccess {
            if (it.isSuccessful) {
                MyApplication.prefs.token = it.headers()[HeaderUtil.AUTHORIZATION].toString()
                Log.d(Constant.TAG, "token is ${MyApplication.prefs.token}")
                onLoginSuccess()
            } else {
                val errorBody = it.errorBody()?.string() ?: "서버 에러. 잠시 후 다시 시도해 주세요."
                try {
                    val jsonObject = JSONObject(errorBody)
                    val message = jsonObject.optString("message", "서버 에러. 잠시 후 다시 시도해 주세요.")
                    onLoginFailure(message)
                } catch (e: Exception) {
                    onLoginFailure("서버 에러. 잠시 후 다시 시도해 주세요.")
                }
            }
        }.onFailure {
            Log.e(Constant.TAG, "LoginViewModel error : $it")
            onLoginFailure(it.message.toString())
        }
    }
}