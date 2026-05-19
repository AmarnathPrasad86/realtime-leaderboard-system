package com.amr.leaderboard.feature.leaderboard.di

import com.amr.leaderboard.feature.leaderboard.data.repository.LeaderboardRepositoryImpl
import com.amr.leaderboard.feature.leaderboard.domain.repository.LeaderboardRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LeaderboardModule {

    @Binds
    @Singleton
    abstract fun bindLeaderboardRepository(
        leaderboardRepositoryImpl: LeaderboardRepositoryImpl
    ): LeaderboardRepository
}
