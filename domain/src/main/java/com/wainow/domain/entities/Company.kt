package com.wainow.domain.entities

interface Company{
    companion object{
        const val TAG: String = "DebugLogs"
    }
}

interface CompanyInfo : Company

data class CompanyItem(val img: String, val id: Int, val name: String) : CompanyInfo

data class CompanyDescription(val id: Int,
                              val name: String,
                              val img: String?,
                              val description: String?,
                              val lat: Double?,
                              val lon: Double?,
                              val www: String?,
                              val phone: String?
                              ) : CompanyInfo
