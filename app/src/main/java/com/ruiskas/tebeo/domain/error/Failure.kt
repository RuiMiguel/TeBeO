package com.ruiskas.tebeo.domain.error

sealed class Failure(val message: String? = null) {
    class GenericFailure(val code: Int = 0, message: String? = null) : Failure(message)

    abstract class FeatureFailure(message: String? = null): Failure(message)
}