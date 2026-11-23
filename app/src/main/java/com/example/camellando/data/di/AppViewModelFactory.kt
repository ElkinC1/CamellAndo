package com.example.camellando.data.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.camellando.data.repository.AuthRepository
import com.example.camellando.ui.auth.LoginViewModel

/**
 * Factory manual únicamente para ViewModels pendientes de migración.
 * HomeViewModel y CreatePostViewModel se gestionan e inyectan mediante Hilt.
 */
class AppViewModelFactory(
    private val authRepository: AuthRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->
                LoginViewModel(authRepository) as T

            else -> throw IllegalArgumentException(
                "ViewModel no registrado en AppViewModelFactory: ${modelClass.name}"
            )
        }
}
