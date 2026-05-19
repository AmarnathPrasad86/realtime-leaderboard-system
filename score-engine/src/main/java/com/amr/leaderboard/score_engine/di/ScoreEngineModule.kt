package com.amr.leaderboard.score_engine.di

import com.amr.leaderboard.score_engine.data.engine.FakeGameEngine
import com.amr.leaderboard.score_engine.data.repository.ScoreRepositoryImpl
import com.amr.leaderboard.score_engine.domain.engine.GameEngine
import com.amr.leaderboard.score_engine.domain.repository.ScoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ScoreEngineModule {

    @Binds
    @Singleton
    abstract fun bindGameEngine(
        fakeGameEngine: FakeGameEngine
    ): GameEngine

    @Binds
    @Singleton
    abstract fun bindScoreRepository(
        scoreRepositoryImpl: ScoreRepositoryImpl
    ): ScoreRepository
}
