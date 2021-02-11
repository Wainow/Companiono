package com.wainow.companiono.description

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.wainow.domain.entities.CompanyDescription


class CompanyDescriptionViewModel() : ViewModel() {
    private val companyData : MutableLiveData<CompanyDescription> = MutableLiveData()

    fun getData() = companyData

    fun setData(companyDescription: CompanyDescription) {
        companyData.value = companyDescription
    }
}