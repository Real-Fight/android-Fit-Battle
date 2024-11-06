package com.qpeterp.fitbattle.presentation.features.auth.register.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.qpeterp.fitbattle.domain.usecase.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUsCase: RegisterUseCase,
) : ViewModel() {
    private val _id = mutableStateOf("")
    val id get() = _id.value

    private val _password = mutableStateOf("")
    val password get() = _password.value

    private val _name = mutableStateOf("")
    val name get() = _name.value

    fun inputId(newId: String) {
        _id.value = newId
    }

    fun inputPassword(newPassword: String) {
        _password.value = newPassword
    }

    fun inputName(newName: String) {
        _name.value = newName
    }

    fun clear() {
        _id.value = ""
        _password.value = ""
        _name.value = ""
    }

    suspend fun register(
        onRegisterSuccess: () -> Unit,
        onRegisterFailure: (String) -> Unit
        ) {
        val result = registerUsCase(
            param = RegisterUseCase.Param(
                loginId = id,
                password = password,
                name = name
            )
        )

        result.onSuccess {
            if (it.isSuccessful) {
                onRegisterSuccess()
            } else {
                val errorBody = it.errorBody()?.string() ?: "서버 에러"
                val message = JSONObject(errorBody).optString("message", errorBody)
                onRegisterFailure(message)
            }
        }.onFailure {
            onRegisterFailure(it.message.toString())
        }
    }
}