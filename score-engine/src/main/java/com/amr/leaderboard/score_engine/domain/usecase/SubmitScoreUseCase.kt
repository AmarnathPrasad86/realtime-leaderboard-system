package com.amr.leaderboard.score_engine.domain.usecase

import com.amr.leaderboard.score_engine.domain.model.Score
import com.amr.leaderboard.score_engine.domain.repository.ScoreRepository
import javax.inject.Inject

class SubmitScoreUseCase @Inject constructor(
    private val repository: ScoreRepository
) {
    suspend operator fun invoke(score: Score) {
        repository.submitScore(score)
    }
}
