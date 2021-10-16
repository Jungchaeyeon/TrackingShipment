package com.jcy.trackingshipment.presentation.trackingItem

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.work.*
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.jcy.trackingshipment.R
import com.jcy.trackingshipment.data.entity.TrackingDetail
import com.jcy.trackingshipment.databinding.ActivityTrackingBinding
import com.jcy.trackingshipment.extension.ToastUtil
import com.jcy.trackingshipment.extension.ToastUtil.Companion.showShort
import com.jcy.trackingshipment.extension.hideKeyboard
import com.jcy.trackingshipment.presentation.base.BaseActivity
import com.jcy.trackingshipment.presentation.trackingItem.adapter.DeliveryItemAdapter
import com.jcy.trackingshipment.presentation.trackinghistory.TrackingHistoryFragment
import com.jcy.trackingshipment.work.TrackingCheckWorker
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import java.util.concurrent.TimeUnit


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
        initWorker()
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
    private fun initWorker(){

        val dailyTrackinCheckRequest =
            //PeriodicWorkRequestBuilder - 여러 번 실행할 작업의 요청을 나타내는 클래스
            PeriodicWorkRequestBuilder<TrackingCheckWorker>(1, TimeUnit.DAYS)
                .setInitialDelay(3, TimeUnit.HOURS)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR, //일정시간, 몇초뒤에몇초뒤에
                    PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()

        WorkManager.getInstance(this) //이렇게 진행해주십시요.. 를 지정
            .enqueueUniquePeriodicWork( //반복하는 작업이므로
                "DailyTrackingCheck",
                ExistingPeriodicWorkPolicy.KEEP, //이미 존재하는 작업이면 기존의 작업을 유지
                dailyTrackinCheckRequest
            )
    }
    private fun initAdapters(){
        adapter = DeliveryItemAdapter(this,{

            val array = arrayListOf<TrackingDetail>()
            it.forEach { item->
                array.add(item) }

            TrackingHistoryFragment.newInstance(array)
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
                Log.e("log", viewModel.deliveryResponse.value.toString())
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
                if(isRefreshing == true) showShort("조회 목록이 업데이트 되었습니다:)")
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
    }

    fun onClickMore() = with(binding){
        showAllCompanies()
        showMoreCarrierNameTv.isVisible = false
    }
    fun onClickShowCarrierNameView() = with(binding){
        carrierNameChipGroup.removeAllViews()
        showMoreCarrierNameTv.isVisible = true
        viewModel.mutableCompanyListIsShowing.value = true
        showRecommendCompanies()
        pickCarrierNameLayout.isVisible = true
        hideKeyboard(this.root)
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
            viewModel.mutableTrackingState.value = TrackingState.Success
        }
    }



}