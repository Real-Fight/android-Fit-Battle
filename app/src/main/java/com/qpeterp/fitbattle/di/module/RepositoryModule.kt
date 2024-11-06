package com.qpeterp.fitbattle.di.module

import com.qpeterp.fitbattle.data.repository.AuthRepositoryImpl
import com.qpeterp.fitbattle.data.repository.RankRepositoryImpl
import com.qpeterp.fitbattle.domain.repository.AuthRepository
import com.qpeterp.fitbattle.domain.repository.RankRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun providesAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Singleton
    @Binds
    abstract fun providesRankRepository(
        rankRepositoryImpl: RankRepositoryImpl
    ): RankRepository
}