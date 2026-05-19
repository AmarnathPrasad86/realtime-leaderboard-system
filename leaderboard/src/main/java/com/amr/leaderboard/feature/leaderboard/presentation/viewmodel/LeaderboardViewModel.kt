package com.amr.leaderboard.feature.leaderboard.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.amr.leaderboard.core.presentation.BaseViewModel
import com.amr.leaderboard.feature.leaderboard.domain.usecase.GetLeaderboardUseCase
import com.amr.leaderboard.feature.leaderboard.presentation.screen.LeaderboardState
import com.amr.leaderboard.score_engine.domain.model.Score
import com.amr.leaderboard.score_engine.domain.usecase.ObserveScoreUpdatesUseCase
import com.amr.leaderboard.score_engine.domain.usecase.SubmitScoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel @Inject constructor(
    private val getLeaderboardUseCase: GetLeaderboardUseCase,
    private val observeScoreUpdatesUseCase: ObserveScoreUpdatesUseCase,
    private val submitScoreUseCase: SubmitScoreUseCase
) : BaseViewModel<LeaderboardState>(LeaderboardState()) {

    init {
        // Start observing score updates to trigger the engine
        observeScoreUpdatesUseCase()
            .launchIn(viewModelScope)

        // Observe ranked leaderboard data
        observeLeaderboard()
    }

    private fun observeLeaderboard() {
        updateState { copy(isLoading = true) }
        getLeaderboardUseCase()
            .onEach { entries ->
                updateState { copy(isLoading = false, entries = entries) }
            }
            .launchIn(viewModelScope)
    }

    fun addRandomScore() {
        viewModelScope.launch {
            val randomUser = (1..5).random().toString()
            val points = (10..100).random().toLong()
            submitScoreUseCase(
                Score(
                    id = UUID.randomUUID().toString(),
                    userId = randomUser,
                    points = points,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }
}
