package com.example.cakratreasury.di

import com.example.cakratreasury.data.repository.AuthRepositoryImpl
import com.example.cakratreasury.data.repository.TreasuryRepositoryImpl
import com.example.cakratreasury.domain.repository.AuthRepository
import com.example.cakratreasury.domain.repository.TreasuryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTreasuryRepository(
        treasuryRepositoryImpl: TreasuryRepositoryImpl
    ): TreasuryRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}
