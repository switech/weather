package com.weather.retrofit.model

import java.io.Serializable

class ErrorObject : Serializable {
    var message: String? = null
    var status = 0
    var code = 0
    var developerMessage: String? = null
    var field1: String? = null
    var field2: String? = null
}