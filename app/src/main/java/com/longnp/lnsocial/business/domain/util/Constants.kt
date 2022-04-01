package com.longnp.lnsocial.business.domain.util

import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject

class Constants {
    companion object {
        const val BASE_URL = "http://192.168.1.87:8080/api/" // Base url for api

        const val GALLERY_REQUEST_CODE = 2011
        const val READ_STORAGE_REQUEST_CODE = 3012

        val PARAMS_RERQUEST_BODY = JSONObject()
            .put("userid", "ukXlOlDZ0QD12YsSF2Xc")
            .put("access_token", "l0w0sjek41.21235317272802")
            .put("auth_profile_id", "ewN4ptN4dbZXWH2ps22c")

        fun getRequestBodyAuth(paramsRequestBody: String): RequestBody {
            return RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                paramsRequestBody
            )
        }
    }
}