package com.example.dai_pc.android_test.ultil

import com.example.dai_pc.android_test.base.Constant


class ErrorEnvelope @JvmOverloads constructor(val code: Int, val message: String?, private val throwable: Throwable? = null) {

    constructor(message: String?) : this(Constant.ErrorCode.UNKNOWN, message) {}
}
