package com.amr.leaderboard.score_engine.domain.usecase

import com.amr.leaderboard.score_engine.domain.model.Score
import com.amr.leaderboard.score_engine.domain.repository.ScoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveScoreUpdatesUseCase @Inject constructor(
    private val repository: ScoreRepository
) {
    operator fun invoke(): Flow<Score> = repository.getScoreUpdates()
}
