package com.qpeterp.fitbattle.presentation.features.auth.register.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.qpeterp.fitbattle.domain.usecase.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _registerResult = mutableStateOf<Result<Unit>?>(null)
    val registerResult get() = _registerResult.value

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
        _registerResult.value = null
    }

    suspend fun register(loginId: String, password: String, name: String) {
        registerUsCase(
            param = RegisterUseCase.Param(
                loginId = loginId,
                password = password,
                name = name
            )
        ).onSuccess {
            _registerResult.value = Result.success(Unit)
        }.onFailure {
            _registerResult.value = Result.failure(it)
        }
    }
}