package com.amr.leaderboard.core.domain.usecase

import kotlinx.coroutines.flow.Flow

interface BaseUseCase<in Parameter, out Result> {
    operator fun invoke(parameter: Parameter): Flow<Result>
}
