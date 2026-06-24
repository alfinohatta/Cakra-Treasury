package com.example.cakratreasury.domain.repository

import com.example.cakratreasury.domain.model.User
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val currentUser: StateFlow<User?>
    suspend fun login(email: String, password: String): Boolean
    fun logout()
}
