package com.jcy.trackingshipment.presentation.trackingItem

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jcy.trackingshipment.data.entity.ShippingCompanies
import com.jcy.trackingshipment.data.entity.ShippingCompany
import com.jcy.trackingshipment.data.repository.ShippingCompanyRepository
import com.jcy.trackingshipment.presentation.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TrackingViewModel(
    private val trackingRepository: ShippingCompanyRepository
): BaseViewModel() {

    val mutableTrackingState = MutableLiveData<TrackingState>(TrackingState.Uninitialized)
    var mutableShippingCompany = MutableLiveData<List<ShippingCompany>>()
    var shippingCompanies : List<ShippingCompany> = listOf()

    override fun fetchData(): Job = viewModelScope.launch {
        getShippingCompanies()
    }
     fun getShippingCompanies() =viewModelScope.launch {
         mutableTrackingState.value = TrackingState.Loading
         shippingCompanies = trackingRepository.getShippingCompananies()
         mutableShippingCompany.postValue(shippingCompanies)
    }

}