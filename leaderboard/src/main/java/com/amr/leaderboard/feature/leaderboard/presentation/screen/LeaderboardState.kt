package com.amr.leaderboard.feature.leaderboard.presentation.screen

import com.amr.leaderboard.feature.leaderboard.domain.model.LeaderboardEntry

data class LeaderboardState(
    val isLoading: Boolean = false,
    val entries: List<LeaderboardEntry> = emptyList(),
    val error: String? = null
)
