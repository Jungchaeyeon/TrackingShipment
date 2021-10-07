package com.jcy.trackingshipment.presentation.trackingItem

import com.jcy.trackingshipment.databinding.ActivityTrackingBinding
import com.jcy.trackingshipment.presentation.base.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel

class TrackingActivity : BaseActivity<TrackingViewModel, ActivityTrackingBinding>() {


    override val viewModel by viewModel<TrackingViewModel>()

    override fun getViewBinding(): ActivityTrackingBinding = ActivityTrackingBinding.inflate(layoutInflater)

    override fun initViews() = with(binding){
       //todo: chipGroupListener

        //todo: textChangeListener
        setChipGroup(this)
    }
    override fun observeData() {

    }
    private fun setChipGroup(binding: ActivityTrackingBinding){

    }


}