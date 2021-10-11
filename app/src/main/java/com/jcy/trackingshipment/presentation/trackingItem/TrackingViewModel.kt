package com.jcy.trackingshipment.presentation.trackingItem

import android.opengl.ETC1.isValid
import android.util.Log
import androidx.lifecycle.*
import com.jcy.trackingshipment.data.entity.ShippingCompanies
import com.jcy.trackingshipment.data.entity.ShippingCompany
import com.jcy.trackingshipment.data.entity.TrackingInfo
import com.jcy.trackingshipment.data.entity.TrackingItem
import com.jcy.trackingshipment.data.entity.model.Delivery
import com.jcy.trackingshipment.data.repository.ShippingCompanyRepository
import com.jcy.trackingshipment.data.repository.TrackingItemRepository
import com.jcy.trackingshipment.extension.addSourceList
import com.jcy.trackingshipment.presentation.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TrackingViewModel(
    private val shippingCompanyRepository: ShippingCompanyRepository,
    private val trackingItemRepository: TrackingItemRepository
): BaseViewModel() {

    val mutableTrackingState = MutableLiveData<TrackingState>(TrackingState.Uninitialized)
    val mutableCompanyListIsShowing = MutableLiveData<Boolean>(false)
    val companyListIsShowing : LiveData<Boolean>
        get() = mutableCompanyListIsShowing
    val mutableShippingCompany = MutableLiveData<List<ShippingCompany>>()
    var shippingCompanies : List<ShippingCompany> = listOf()

    val carrierName = MutableLiveData<String>().apply { value= "택배회사선택" }
    val trackId = MutableLiveData<String>()
    var selectedShippingCompany : ShippingCompany? = null

    val deliveryResponse = MutableLiveData<TrackingInfo?>()
    val isRefreshing: MutableLiveData<Boolean?> = MutableLiveData()

    val allTrackingItems = liveData {
        val fromDB: LiveData<List<Delivery>> = trackingItemRepository.getAllTrackingItems().asLiveData()
        emitSource(fromDB)
    }

    var isValidInput = MediatorLiveData<Boolean>().apply {
        addSourceList(carrierName, trackId) { isValid() }
    }

    fun onClickTracking()= viewModelScope.launch {
        mutableTrackingState.postValue(TrackingState.Loading)
        selectedShippingCompany = shippingCompanies?.find { it.name == carrierName.value }
        deliveryResponse.postValue(
            handle {  trackingItemRepository.getTrackingInformation(selectedShippingCompany?.code!!, trackId.value?.trim()!!) }
        )
    }
    fun insert() = viewModelScope.launch {
        selectedShippingCompany?.let {
            TrackingItem(
                trackId.value!!,
                it
            )
        }?.let {
            trackingItemRepository.insertTrackingItem(
                it
            )
        }
        }


    fun rollback(delivery: Delivery) = viewModelScope.launch { trackingItemRepository.rollback(delivery) }

    fun delete(trackingItem: Delivery) = viewModelScope.launch { trackingItemRepository.deleteTrackingItem(trackingItem) }


    override fun fetchData(): Job = viewModelScope.launch {
        getShippingCompanies()
        updateAll()
    }

    fun updateAll() = viewModelScope.launch {
        allTrackingItems.value
                .takeIf { !it.isNullOrEmpty() }
                ?.let {
                    update(it)
                    isRefreshing.postValue(true)
                } ?: isRefreshing.postValue(false)
    }
    private suspend fun update(list: List<Delivery>){
        list.map {
            handle {trackingItemRepository.getTrackingInformation(it.invoice, it.carrierName,) }?.toDelivery(
                it.id, it.carrierName, it.itemName,it.status,it.invoice,it.company)
            }.apply { trackingItemRepository.updateAll(this.filterNotNull()) }
        }


     fun getShippingCompanies() =viewModelScope.launch {
         mutableTrackingState.value = TrackingState.Loading
         shippingCompanies = shippingCompanyRepository.getShippingCompananies()
         mutableShippingCompany.postValue(shippingCompanies)
    }
    private fun isValid() = !carrierName.value.isNullOrEmpty() && !trackId.value.isNullOrEmpty()
}