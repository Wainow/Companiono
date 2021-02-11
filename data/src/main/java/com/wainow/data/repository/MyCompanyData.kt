package com.wainow.data.repository

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.Gson
import com.wainow.data.api.APIService
import com.wainow.data.entities.CompanyData
import com.wainow.data.entities.ResponseCallback
import com.wainow.domain.entities.Company
import com.wainow.domain.entities.Company.Companion.TAG
import com.wainow.domain.entities.CompanyDescription
import com.wainow.domain.entities.CompanyItem
import com.wainow.domain.repositories.Data
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


object MyCompanyData : CompanyData(), Company{

    override fun getItemById(
        id: Int,
        callback: (Array<CompanyDescription>) -> Unit,
        errorCallback: (String) -> Unit
    ) {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        retrofit.create(APIService::class.java).getCompanyById(id)
            ?.enqueue(object : ResponseCallback<String?>() {
                override fun onSuccessResponse(call: Call<String?>?, response: Response<String?>?) {
                    val result = response?.body()
                        ?.replace("\" ", "' ")
                        ?.replace(" \"", " '")
                        ?.replace(" ',", " \",")
                    Log.d(
                        TAG,
                        "MyCompanyData: OnResponse: response is successful: $result"
                    )
                    callback(Gson().fromJson(result, Array<CompanyDescription>::class.java))
                }

                override fun onFailureResponse(call: Call<String?>?, response: Response<String?>?) {
                    Log.d(
                        TAG,
                        "MyCompanyData: OnResponse: response is not successful: ${response?.code()}"
                    )
                    errorCallback("Error: ${response?.code()}")
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    Log.d(TAG, "MyCompanyData: OnFailure: ${t.message}")
                    errorCallback("Error: ${t.message}")
                }
            })
    }

    override fun getItemList(callback: (List<CompanyItem?>?) -> Unit, errorCallback: (String) -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(APIService::class.java).getCompanies()
            ?.enqueue(object : ResponseCallback<List<CompanyItem?>?>() {
                override fun onSuccessResponse(
                    call: Call<List<CompanyItem?>?>?,
                    response: Response<List<CompanyItem?>?>?
                ) {
                    Log.d(TAG, "MyCompanyData: OnResponse: response is successful")
                    callback(response?.body())
                }

                override fun onFailureResponse(
                    call: Call<List<CompanyItem?>?>?,
                    response: Response<List<CompanyItem?>?>?
                ) {
                    Log.d(
                        TAG,
                        "MyCompanyData: OnResponse: response is not successful: ${response?.code()}"
                    )
                    errorCallback("Error: ${response?.code()}")
                }

                override fun onFailure(call: Call<List<CompanyItem?>?>, t: Throwable) {
                    Log.d(TAG, "MyCompanyData: OnFailure: ${t.message}")
                    errorCallback("Error: ${t.message}")
                }
            })
    }
}
