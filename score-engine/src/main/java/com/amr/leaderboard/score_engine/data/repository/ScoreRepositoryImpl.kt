package com.amr.leaderboard.score_engine.data.repository

import com.amr.leaderboard.score_engine.data.datasource.LocalScoreDataSource
import com.amr.leaderboard.score_engine.domain.engine.GameEngine
import com.amr.leaderboard.score_engine.domain.model.Score
import com.amr.leaderboard.score_engine.domain.repository.ScoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ScoreRepositoryImpl @Inject constructor(
    private val gameEngine: GameEngine,
    private val dataSource: LocalScoreDataSource
) : ScoreRepository {

    override fun getScoreUpdates(): Flow<Score> {
        return gameEngine.startScoring().onEach { score ->
            dataSource.addScore(score)
        }
    }

    override fun getAllScores(): Flow<List<Score>> {
        return dataSource.scores
    }

    override suspend fun submitScore(score: Score) {
        dataSource.addScore(score)
    }
}
