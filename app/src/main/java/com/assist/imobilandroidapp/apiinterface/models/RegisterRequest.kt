package com.assist.imobilandroidapp.apiinterface.models

data class RegisterRequest(
    var email: String?,
    var password: String?,
    var nameDevice: String?,
    var deviceType: String?,
    var location: String?,
    var status: Boolean
)