package com.amr.leaderboard.core.di

import com.amr.leaderboard.core.domain.dispatchers.DefaultDispatcherProvider
import com.amr.leaderboard.core.domain.dispatchers.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApplicationScope

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()

    @Provides
    @Singleton
    @ApplicationScope
    fun provideApplicationScope(
        dispatcherProvider: DispatcherProvider
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcherProvider.default)
}
