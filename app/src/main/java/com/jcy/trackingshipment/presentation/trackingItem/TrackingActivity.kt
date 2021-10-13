package com.jcy.trackingshipment.presentation.trackingItem

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.jcy.trackingshipment.R
import com.jcy.trackingshipment.data.entity.TrackingDetail
import com.jcy.trackingshipment.data.entity.model.Delivery
import com.jcy.trackingshipment.databinding.ActivityTrackingBinding
import com.jcy.trackingshipment.extension.ToastUtil
import com.jcy.trackingshipment.extension.ToastUtil.Companion.showShort
import com.jcy.trackingshipment.presentation.base.BaseActivity
import com.jcy.trackingshipment.presentation.trackingItem.adapter.DeliveryItemAdapter
import com.jcy.trackingshipment.presentation.trackinghistory.TrackingHistoryFragment
import org.koin.android.viewmodel.ext.android.viewModel

class TrackingActivity : BaseActivity<TrackingViewModel, ActivityTrackingBinding>() {


    override val viewModel by viewModel<TrackingViewModel>()
    private lateinit var adapter: DeliveryItemAdapter
    override fun getViewBinding(): ActivityTrackingBinding = ActivityTrackingBinding.inflate(layoutInflater)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.vi = this
        binding.lifecycleOwner = this
        observeState()
        initAdapters()
        initSwipeRefresh()
    }

    override fun initViews() = with(binding){
        binding?.carrierNameChipGroup?.setOnCheckedChangeListener { group, checkedId ->
            group.findViewById<Chip>(checkedId)?.let {
                viewModel.carrierName.postValue(it.text.toString())
            } ?: viewModel.carrierName.postValue(null)
        }

        viewModel.mutableShippingCompany.observe(this@TrackingActivity){
            showRecommendCompanies()
        }

    }
    private fun initAdapters(){
        adapter = DeliveryItemAdapter(this,{delivery ->
            TrackingHistoryFragment.newInstance(delivery.trackingHistorys as ArrayList<TrackingDetail>)
                .show(supportFragmentManager, null)
        },{delivery -> viewModel.delete(delivery)
            Snackbar.make(
                this, binding.root, getString(R.string.delete_complete), Snackbar.LENGTH_LONG,

            ).setAction("취소"){viewModel.rollback(delivery)}
                .show()
        })
        binding.trakingItemRv.adapter = adapter
    }
    private fun observeState() = viewModel.mutableTrackingState.observe(this){
        when(it){

            is TrackingState.Loading ->{
                handlingLoadingState()
            }
            is TrackingState.Success ->{
                handlingSuccessState()
            }
            is TrackingState.Error ->{
                Toast.makeText(this, it.messageId, Toast.LENGTH_SHORT).show()
            }
            else ->Unit
        }
    }

    override fun observeData() = with(viewModel){

        isRefreshing.observe(::getLifecycle){isRefreshing->
            isRefreshing?.let {
            if(isRefreshing == true)showShort("조회 목록이 업데이트 되었습니다:)")
            else showShort("조회 목록이 비었습니다.")
                binding.swipeLayout.isRefreshing = false
            }
        }
        allTrackingItems.observe(::getLifecycle) {
                adapter.setItemList(it)
                viewModel.mutableTrackingState.value = TrackingState.Success
                onClickHideCarrierNameView()
            }
        deliveryResponse.observe(::getLifecycle) {
            it?.let {
                Log.e("getTrackingInfo", deliveryResponse.value.toString())
                insert()
                ToastUtil.showLong(R.string.add_new_delivery_insert_complete)
                viewModel.mutableTrackingState.value = TrackingState.Success
            }
        }

    }
    private fun showRecommendCompanies()= with(binding){

        viewModel.shippingCompanies.take(20).forEach {company->
            carrierNameChipGroup?.addView(
                Chip(this@TrackingActivity).apply {
                    text =company.name
                    isCheckable = true
                    isClickable = true
                    setTextColor(Color.WHITE)
                    setEnsureMinTouchTargetSize(false)
                    checkedIcon = null
                    chipBackgroundColor = resources.getColorStateList(R.color.color_add_chip_bg)
                }
            )
        }
        viewModel.mutableCompanyListIsShowing.value = true
        pickCarrierNameLayout.isVisible = true
    }
    private fun showAllCompanies()= with(binding){

        viewModel.shippingCompanies.take(30).forEach {company->
            carrierNameChipGroup?.addView(
                Chip(this@TrackingActivity).apply {
                    text =company.name
                    isCheckable = true
                    isClickable = true
                    setTextColor(Color.WHITE)
                    setEnsureMinTouchTargetSize(false)
                    checkedIcon = null
                    chipBackgroundColor = resources.getColorStateList(R.color.color_add_chip_bg)
                }
            )
        }
        viewModel.mutableCompanyListIsShowing.value = true
        pickCarrierNameLayout.isVisible = true
    }

    fun onClickMore() = with(binding){
        showAllCompanies()
        showMoreCarrierNameTv.isVisible = false
    }
    fun onClickShowCarrierNameView() = with(binding){
        carrierNameChipGroup.removeAllViews()
        showMoreCarrierNameTv.isVisible = true
        showRecommendCompanies()
    }
    fun onClickHideCarrierNameView() = with(binding){
        pickCarrierNameLayout.isVisible = false
        viewModel.mutableCompanyListIsShowing.value = false
    }
    private fun handlingLoadingState() = with(binding){
        progressBar.isVisible = true
    }
    private fun handlingSuccessState() = with(binding){
        progressBar.isVisible = false
    }

    private fun initSwipeRefresh() =with(binding){
        swipeLayout.apply {
            setColorSchemeResources(android.R.color.holo_blue_light)
            setOnRefreshListener { viewModel.updateAll() }
            viewModel.mutableTrackingState.postValue(TrackingState.Success)
        }
    }



}