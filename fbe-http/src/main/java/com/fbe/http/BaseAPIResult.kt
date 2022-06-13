package com.fbe.http

import com.fbe.http.exception.ResponseThrowable
import com.google.gson.annotations.SerializedName

/**
 * 框架内部对API Result的实现，如果返回的不一致，可自行实现APIResult即可
 * 主要自行实现：isSuccess 定义业务成功code，以及失败的Failure
 */
data class BaseAPIResult<T>(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    override val data: T? = null,
    @SerializedName("message")
    val message: String? = null
) : APIResult<T, BaseAPIResult.Failure> {
    data class Failure(val code: Int, val message: String?) : APIResult.Failure {
        override fun getThrowable(): ResponseThrowable {
            return ResponseThrowable(code, message ?: "网络异常")
        }
    }

    override val isSuccess: Boolean
        get() = code == 0

    override val failure: Failure?
        get() = if (isFailure) Failure(code, message) else null
}
