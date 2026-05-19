package com.amr.leaderboard.score_engine.data.engine

import com.amr.leaderboard.core.domain.dispatchers.DispatcherProvider
import com.amr.leaderboard.score_engine.domain.engine.GameEngine
import com.amr.leaderboard.score_engine.domain.model.Score
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

class FakeGameEngine @Inject constructor(
    private val dispatcherProvider: DispatcherProvider
) : GameEngine {

    private val userScores = mutableMapOf<String, Long>()
    // Including "me" for Amarnath and 4 others to make total 5 users
    private val userIds = listOf("me", "2", "3", "4", "5")

    override fun startScoring(): Flow<Score> = flow {
        while (true) {
            val userId = userIds.random()
            val currentScore = userScores.getOrDefault(userId, 0L)
            val increment = Random.nextLong(10, 100)
            val newScoreValue = currentScore + increment
            
            userScores[userId] = newScoreValue
            
            val score = Score(
                id = UUID.randomUUID().toString(),
                userId = userId,
                points = newScoreValue,
                timestamp = System.currentTimeMillis()
            )
            
            emit(score)
            
            val nextDelay = Random.nextLong(500, 2000)
            delay(nextDelay)
        }
    }.flowOn(dispatcherProvider.default)
}
