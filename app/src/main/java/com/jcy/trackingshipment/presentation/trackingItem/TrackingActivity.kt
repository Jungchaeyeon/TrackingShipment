package com.jcy.trackingshipment.presentation.trackingItem

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.material.chip.Chip
import com.jcy.trackingshipment.R
import com.jcy.trackingshipment.databinding.ActivityTrackingBinding
import com.jcy.trackingshipment.presentation.base.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel

class TrackingActivity : BaseActivity<TrackingViewModel, ActivityTrackingBinding>() {


    override val viewModel by viewModel<TrackingViewModel>()

    override fun getViewBinding(): ActivityTrackingBinding = ActivityTrackingBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.vi = this
        binding.lifecycleOwner = this

    }

    override fun initViews() = with(binding){
        binding?.carrierNameChipGroup?.setOnCheckedChangeListener { group, checkedId ->
            group.findViewById<Chip>(checkedId)?.let {
                viewModel.carrierName.postValue(it.text.toString())
            } ?: viewModel.carrierName.postValue(null)
        }

        viewModel.mutableShippingCompany.observe(this@TrackingActivity){
            showRecommendCompanies()
            viewModel.mutableTrackingState.value = TrackingState.Success
        }

    }
    override fun observeData() = viewModel.mutableTrackingState.observe(this){
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



}