package com.amr.leaderboard.feature.leaderboard.data.repository

import com.amr.leaderboard.core.di.ApplicationScope
import com.amr.leaderboard.core.domain.util.RankingCalculator
import com.amr.leaderboard.feature.leaderboard.domain.model.LeaderboardEntry
import com.amr.leaderboard.feature.leaderboard.domain.repository.LeaderboardRepository
import com.amr.leaderboard.score_engine.domain.repository.ScoreRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LeaderboardRepositoryImpl @Inject constructor(
    private val scoreRepository: ScoreRepository,
    @ApplicationScope private val scope: CoroutineScope
) : LeaderboardRepository {

    /**
     * Consumes scores from the engine, groups them by user, 
     * takes the latest cumulative score, and applies ranking.
     */
    override val leaderboard: StateFlow<List<LeaderboardEntry>> = scoreRepository.getAllScores()
        .map { scores ->
            val unrankedEntries = scores.groupBy { it.userId }
                .map { (userId, userScores) ->
                    // Since engine provides the total running score in each update,
                    // we take the most recent score entry for that user.
                    val latestEntry = userScores.maxByOrNull { it.timestamp }
                    val totalScore = latestEntry?.points ?: 0L
                    
                    LeaderboardEntry(
                        userId = userId,
                        username = "User $userId",
                        totalScore = totalScore,
                        rank = 0
                    )
                }

            RankingCalculator.applyRanking(
                items = unrankedEntries,
                scoreSelector = { it.totalScore },
                rankAssigner = { entry, rank -> entry.copy(rank = rank) }
            )
        }
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
