package com.wainow.data.entities

import com.wainow.data.api.APIService
import com.wainow.domain.entities.CompanyDescription
import com.wainow.domain.entities.CompanyItem
import com.wainow.domain.repositories.Data

abstract class CompanyData: Data {
    companion object{
        const val URL = "https://lifehack.studio/"
    }

    abstract override fun getItemById(
        id: Int,
        callback: (Array<CompanyDescription>) -> Unit,
        errorCallback: (String) -> Unit
    )

    abstract override fun getItemList(
        callback: (List<CompanyItem?>?) -> Unit,
        errorCallback: (String) -> Unit
    )
}