package com.amr.leaderboard.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.amr.leaderboard.feature.leaderboard.presentation.screen.LeaderboardScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Leaderboard.route,
        modifier = modifier
    ) {
        composable(Screen.Leaderboard.route) {
            LeaderboardScreen()
        }
    }
}
