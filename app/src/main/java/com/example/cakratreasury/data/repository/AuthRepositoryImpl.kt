package com.example.cakratreasury.data.repository

import com.example.cakratreasury.data.local.dao.UserDao
import com.example.cakratreasury.data.mapper.toDomain
import com.example.cakratreasury.domain.model.User
import com.example.cakratreasury.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : AuthRepository {

    private val _currentUser = MutableStateFlow<User?>(null)
    override val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    override suspend fun login(email: String, password: String): Boolean {
        // Simplified login logic for demo
        val userEntity = userDao.getUserByEmail(email)
        return if (userEntity != null && password == "admin123") { // Mock password check
            _currentUser.value = userEntity.toDomain()
            true
        } else {
            false
        }
    }

    override fun logout() {
        _currentUser.value = null
    }
}
