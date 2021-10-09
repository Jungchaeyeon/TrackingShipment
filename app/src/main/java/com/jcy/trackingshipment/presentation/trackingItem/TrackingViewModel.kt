package com.jcy.trackingshipment.presentation.trackingItem

import android.opengl.ETC1.isValid
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jcy.trackingshipment.data.entity.ShippingCompanies
import com.jcy.trackingshipment.data.entity.ShippingCompany
import com.jcy.trackingshipment.data.repository.ShippingCompanyRepository
import com.jcy.trackingshipment.extension.addSourceList
import com.jcy.trackingshipment.presentation.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TrackingViewModel(
    private val trackingRepository: ShippingCompanyRepository
): BaseViewModel() {

    val mutableTrackingState = MutableLiveData<TrackingState>(TrackingState.Uninitialized)
    val mutableCompanyListIsShowing = MutableLiveData<Boolean>(false)
    val companyListIsShowing : LiveData<Boolean>
        get() = mutableCompanyListIsShowing
    val mutableShippingCompany = MutableLiveData<List<ShippingCompany>>()
    var shippingCompanies : List<ShippingCompany> = listOf()

    val carrierName = MutableLiveData<String>().apply { value= "택배회사선택" }
    val trackId = MutableLiveData<String>()

    var isValidInput = MediatorLiveData<Boolean>().apply {
        addSourceList(carrierName, trackId) { isValid() }
    }

    override fun fetchData(): Job = viewModelScope.launch {
        getShippingCompanies()
    }
     fun getShippingCompanies() =viewModelScope.launch {
         mutableTrackingState.value = TrackingState.Loading
         shippingCompanies = trackingRepository.getShippingCompananies()
         mutableShippingCompany.postValue(shippingCompanies)
    }
    private fun isValid() = !carrierName.value.isNullOrEmpty() && !trackId.value.isNullOrEmpty()
}