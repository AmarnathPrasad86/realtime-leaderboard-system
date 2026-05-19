package com.amr.leaderboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.amr.leaderboard.navigation.NavGraph
import com.amr.leaderboard.ui.theme.LeaderboardTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main entry point for the application.
 * Annotated with @AndroidEntryPoint to enable Hilt dependency injection.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enable edge-to-edge display//
        enableEdgeToEdge()
        
        setContent {
            LeaderboardTheme {
                val navController = rememberNavController()
                
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    // Set up the Navigation Graph
                    NavGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)

                    )
                }
            }
        }
    }
}
