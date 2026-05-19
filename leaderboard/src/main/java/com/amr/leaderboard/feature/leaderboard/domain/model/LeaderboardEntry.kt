package com.amr.leaderboard.feature.leaderboard.domain.model

data class LeaderboardEntry(
    val userId: String,
    val username: String,
    val totalScore: Long,
    val rank: Int
)
