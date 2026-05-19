package com.amr.leaderboard.core.domain.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RankingCalculatorTest {

    data class TestEntry(val id: String, val score: Long, val rank: Int = 0)

    @Test
    fun `applyRanking calculates standard competition ranking correctly`() {
        val input = listOf(
            TestEntry("A", 100),
            TestEntry("B", 100),
            TestEntry("C", 90),
            TestEntry("D", 80)
        )

        val result = RankingCalculator.applyRanking(
            items = input,
            scoreSelector = { it.score },
            rankAssigner = { entry, rank -> entry.copy(rank = rank) }
        )

        assertEquals(1, result[0].rank) // A
        assertEquals(1, result[1].rank) // B (Tie)
        assertEquals(3, result[2].rank) // C (Skips rank 2)
        assertEquals(4, result[3].rank) // D
    }

    @Test
    fun `applyRanking handles empty list`() {
        val result = RankingCalculator.applyRanking(
            items = emptyList<TestEntry>(),
            scoreSelector = { it.score },
            rankAssigner = { entry, rank -> entry.copy(rank = rank) }
        )
        assertTrue(result.isEmpty())
    }

    @Test
    fun `applyRanking handles single item`() {
        val input = listOf(TestEntry("A", 100))
        val result = RankingCalculator.applyRanking(
            items = input,
            scoreSelector = { it.score },
            rankAssigner = { entry, rank -> entry.copy(rank = rank) }
        )
        assertEquals(1, result[0].rank)
    }

    @Test
    fun `applyRanking handles all same scores`() {
        val input = listOf(
            TestEntry("A", 100),
            TestEntry("B", 100),
            TestEntry("C", 100)
        )
        val result = RankingCalculator.applyRanking(
            items = input,
            scoreSelector = { it.score },
            rankAssigner = { entry, rank -> entry.copy(rank = rank) }
        )
        assertTrue(result.all { it.rank == 1 })
    }

    @Test
    fun `applyRanking sorts unsorted input`() {
        val input = listOf(
            TestEntry("A", 80),
            TestEntry("B", 100),
            TestEntry("C", 90)
        )
        val result = RankingCalculator.applyRanking(
            items = input,
            scoreSelector = { it.score },
            rankAssigner = { entry, rank -> entry.copy(rank = rank) }
        )
        assertEquals("B", result[0].id)
        assertEquals(1, result[0].rank)
        assertEquals("C", result[1].id)
        assertEquals(2, result[1].rank)
        assertEquals("A", result[2].id)
        assertEquals(3, result[2].rank)
    }
}
