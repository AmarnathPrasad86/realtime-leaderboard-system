package com.amr.leaderboard.score_engine.domain.model

data class Score(
    val id: String,
    val userId: String,
    val points: Long,
    val timestamp: Long
)
