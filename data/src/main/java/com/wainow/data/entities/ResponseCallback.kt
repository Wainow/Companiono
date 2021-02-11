package com.wainow.data.entities

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class ResponseCallback<T> : Callback<T?> {
    override fun onResponse(call: Call<T?>, response: Response<T?>) {
        if (response.isSuccessful && response.body() != null) {
            onSuccessResponse(call, response)
        } else {
            onFailureResponse(call, response)
        }
    }

    abstract fun onSuccessResponse(call: Call<T?>?, response: Response<T?>?)
    abstract fun onFailureResponse(call: Call<T?>?, response: Response<T?>?)
}