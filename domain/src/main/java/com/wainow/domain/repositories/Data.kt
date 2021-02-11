package com.wainow.domain.repositories
import com.wainow.domain.entities.CompanyDescription
import com.wainow.domain.entities.CompanyInfo
import com.wainow.domain.entities.CompanyItem

interface Data {
    fun getItemById(id: Int, callback: (Array<CompanyDescription>) -> Unit, errorCallback: (String) -> Unit)

    fun getItemList(callback: (List<CompanyItem?>?) -> Unit, errorCallback: (String) -> Unit)
}