package com.amr.leaderboard.core.domain.model

data class User(
    val id: String,
    val username: String,
    val email: String? = null,
    val avatarUrl: String? = null
)
