package com.longnp.lnsocial.business.domain.util

import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject

class Constants {
    companion object {
        const val BASE_URL = "https://lnsocial.herokuapp.com/api/" // Base url for api https://lnsocial.herokuapp.com/

        const val GALLERY_REQUEST_CODE = 2011
        const val READ_STORAGE_REQUEST_CODE = 3012

        fun getParamsBodyAuth(params: HashMap<String, String>): JSONObject {
            val paramsValue = JSONObject()
            params.forEach { (key, value) ->
                paramsValue.put(key, value)
            }
            return paramsValue
        }

        fun getRequestBodyAuth(paramsRequestBody: String): RequestBody {
            return RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                paramsRequestBody
            )
        }
    }
}