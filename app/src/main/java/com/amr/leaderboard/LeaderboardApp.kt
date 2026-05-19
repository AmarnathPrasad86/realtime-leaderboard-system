package com.amr.leaderboard

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * The Application class for the Realtime Leaderboard app.
 * Annotated with @HiltAndroidApp to trigger Hilt's code generation,
 * including a base class for the application that serves as the
 * application-level dependency container.
 */
@HiltAndroidApp
class LeaderboardApp : Application()
