package com.jcy.trackingshipment.presentation.trackinghistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jcy.trackingshipment.R
import com.jcy.trackingshipment.data.entity.TrackingDetail
import com.jcy.trackingshipment.databinding.FragmentTrackingHistoryBinding
import com.jcy.trackingshipment.presentation.base.BaseBottomSheetDialogFragment

class TrackingHistoryFragment: BaseBottomSheetDialogFragment() {

    companion object {
        const val HISTORY = "history"
        fun newInstance(
            historys: ArrayList<TrackingDetail>
        ) = TrackingHistoryFragment().apply {
            arguments = Bundle().apply { putParcelableArrayList(HISTORY, historys) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding<FragmentTrackingHistoryBinding>(
        inflater, R.layout.fragment_tracking_history,container
    ).apply {
        lifecycleOwner = viewLifecycleOwner
        this.trackingHistoryRv.adapter = TrackingHistoryListAdapter().apply {
            setItemList(arguments?.getParcelableArrayList(HISTORY)?: ArrayList<TrackingDetail>())
        }
    }.root
}