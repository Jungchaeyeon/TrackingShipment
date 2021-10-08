package com.jcy.trackingshipment.presentation.trackingItem

import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.material.chip.Chip
import com.jcy.trackingshipment.databinding.ActivityTrackingBinding
import com.jcy.trackingshipment.presentation.base.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel

class TrackingActivity : BaseActivity<TrackingViewModel, ActivityTrackingBinding>() {


    override val viewModel by viewModel<TrackingViewModel>()

    override fun getViewBinding(): ActivityTrackingBinding = ActivityTrackingBinding.inflate(layoutInflater)

    override fun initViews() = with(binding){
       //todo: chipGroupListener

        //todo: textChangeListener
        viewModel.mutableShippingCompany.observe(this@TrackingActivity){
            showCompanies()
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
    private fun showCompanies()= with(binding){

        viewModel.shippingCompanies.forEach {company->
            carrierNameChipGroup?.addView(
                Chip(this@TrackingActivity).apply {
                    text =company.name
                }
            )
        }
        pickCarrierNameLayout.isVisible = true
    }
    private fun handlingLoadingState() = with(binding){
        progressBar.isVisible = true
    }
    private fun handlingSuccessState() = with(binding){
        progressBar.isVisible = false
    }



}