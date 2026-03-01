package com.iqbalwork.ramadhancamp.shared.common.network

class ApiException(
    val httpCode: Int,
    val errorMsg: String,
) : Exception(errorMsg)