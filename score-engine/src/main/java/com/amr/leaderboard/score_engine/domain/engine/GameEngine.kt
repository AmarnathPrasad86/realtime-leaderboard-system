package com.amr.leaderboard.score_engine.domain.engine

import com.amr.leaderboard.score_engine.domain.model.Score
import kotlinx.coroutines.flow.Flow

interface GameEngine {
    fun startScoring(): Flow<Score>
}
