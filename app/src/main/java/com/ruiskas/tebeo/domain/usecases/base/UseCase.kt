package com.ruiskas.tebeo.domain.usecases.base

import arrow.core.Either
import com.ruiskas.tebeo.domain.error.Failure
import kotlinx.coroutines.flow.Flow

abstract class UseCase<out Type, in Params> where Type : Any? {
    abstract fun run(params: Params? = null): Either<Failure, Type>

    @JvmOverloads
    operator fun invoke(
        params: Params? = null
    ): Either<Failure, Type> {
        return run(params)
    }
}

abstract class FlowUseCase<Type, in Params> where Type : Any? {
    abstract fun run(params: Params? = null): Flow<Either<Failure, Type>>

    @JvmOverloads
    operator fun invoke(
        params: Params? = null
    ): Flow<Either<Failure, Type>> {
        return run(params)
    }
}

abstract class SuspendUseCase<Type, in Params> where Type : Any? {
    abstract suspend fun run(params: Params? = null): Either<Failure, Type>

    @JvmOverloads
    suspend operator fun invoke(
        params: Params? = null
    ): Either<Failure, Type> {
        return run(params)
    }
}