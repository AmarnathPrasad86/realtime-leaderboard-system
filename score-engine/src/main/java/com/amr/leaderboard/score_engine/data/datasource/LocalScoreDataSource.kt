package com.amr.leaderboard.score_engine.data.datasource

import com.amr.leaderboard.score_engine.domain.model.Score
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalScoreDataSource @Inject constructor() {
    private val _scores = MutableStateFlow<List<Score>>(emptyList())
    val scores: StateFlow<List<Score>> = _scores.asStateFlow()

    fun addScore(score: Score) {
        _scores.update { currentScores ->
            currentScores + score
        }
    }
}
