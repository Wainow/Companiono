package com.wainow.data.api

import com.wainow.domain.entities.CompanyItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("/test_task/test.php")
    fun getCompanies(): Call<List<CompanyItem?>?>?

    @GET("/test_task/test.php?")
    fun getCompanyById(
        @Query("id") id : Int
    ): Call<String>?
}