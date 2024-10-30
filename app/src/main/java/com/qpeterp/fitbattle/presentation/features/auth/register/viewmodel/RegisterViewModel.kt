package com.qpeterp.fitbattle.presentation.features.auth.register.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    private val _id = mutableStateOf("")
    val id get() = _id.value

    private val _password = mutableStateOf("")
    val password get() = _password.value

    private val _name = mutableStateOf("")
    val name get() = _name.value

    fun updateId(newId: String) {
        _id.value = newId
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun updateName(newName: String) {
        _name.value = newName
    }

    fun clear() {
        _id.value = ""
        _password.value = ""
        _name.value = ""
    }

    fun isComplete() =
        _id.value.isNotBlank() && _password.value.isNotBlank() && _name.value.isNotBlank()
}