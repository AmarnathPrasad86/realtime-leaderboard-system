package com.amr.leaderboard.score_engine.domain.repository

import com.amr.leaderboard.score_engine.domain.model.Score
import kotlinx.coroutines.flow.Flow

interface ScoreRepository {
    fun getScoreUpdates(): Flow<Score>
    fun getAllScores(): Flow<List<Score>>
    suspend fun submitScore(score: Score)
}
