package com.amr.leaderboard.feature.leaderboard.domain.repository

import com.amr.leaderboard.feature.leaderboard.domain.model.LeaderboardEntry
import kotlinx.coroutines.flow.StateFlow

interface LeaderboardRepository {
    /**
     * A StateFlow emitting the current ranked leaderboard.
     */
    val leaderboard: StateFlow<List<LeaderboardEntry>>
}
