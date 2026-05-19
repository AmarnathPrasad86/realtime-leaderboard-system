package com.amr.leaderboard.core.domain.util

/**
 * Standard competition ranking (also known as "1224" ranking).
 * Items with the same score receive the same rank, and the next rank is skipped
 * to maintain the count of participants.
 */
object RankingCalculator {

    /**
     * Applies standard competition ranking to a list of items.
     *
     * Example:
     * Input scores: [100, 100, 90, 80]
     * Output ranks: [1, 1, 3, 4]
     *
     * @param T The type of items to be ranked.
     * @param S The type of the score, must be Comparable.
     * @param items The list of items to rank.
     * @param scoreSelector A lambda to extract the score from an item.
     * @param rankAssigner A lambda to create a new item with the calculated rank.
     * @return A list of ranked items, sorted by score in descending order.
     */
    fun <T, S : Comparable<S>> applyRanking(
        items: List<T>,
        scoreSelector: (T) -> S,
        rankAssigner: (T, Int) -> T
    ): List<T> {
        if (items.isEmpty()) return emptyList()

        // 1. Sort items by score descending
        val sortedItems = items.sortedByDescending { scoreSelector(it) }

        var lastRank = 1
        
        // 2. Calculate and assign ranks
        return sortedItems.mapIndexed { index, item ->
            val currentScore = scoreSelector(item)
            
            val rank = if (index > 0) {
                val previousScore = scoreSelector(sortedItems[index - 1])
                if (currentScore == previousScore) {
                    // Tie: use the same rank as the previous item
                    lastRank
                } else {
                    // No tie: rank is the 1-based index (skips ranks based on previous ties)
                    index + 1
                }
            } else {
                // First item is always rank 1
                1
            }
            
            lastRank = rank
            rankAssigner(item, rank)
        }
    }
}
