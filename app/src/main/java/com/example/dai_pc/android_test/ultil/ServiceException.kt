package com.example.dai_pc.android_test.ultil

class ServiceException(message: String) : Exception(message) {
    val error: ErrorEnvelope

    init {
        error = ErrorEnvelope(message)
    }
}
