package com.amr.leaderboard.feature.leaderboard.domain.usecase

import com.amr.leaderboard.feature.leaderboard.domain.model.LeaderboardEntry
import com.amr.leaderboard.feature.leaderboard.domain.repository.LeaderboardRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetLeaderboardUseCase @Inject constructor(
    private val repository: LeaderboardRepository
) {
    operator fun invoke(): StateFlow<List<LeaderboardEntry>> = repository.leaderboard
}
