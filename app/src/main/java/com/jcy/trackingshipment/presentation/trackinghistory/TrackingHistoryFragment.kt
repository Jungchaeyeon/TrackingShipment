package com.jcy.trackingshipment.presentation.trackinghistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jcy.trackingshipment.R
import com.jcy.trackingshipment.data.entity.TrackingDetail
import com.jcy.trackingshipment.data.entity.model.Timepoint
import com.jcy.trackingshipment.databinding.BottomSheetDialogFragmentBinding
import com.jcy.trackingshipment.presentation.base.BaseBottomSheetDialogFragment
import com.jcy.trackingshipment.presentation.trackinghistory.adapter.TimeLineListAdapter

class TrackingHistoryFragment: BaseBottomSheetDialogFragment() {

    companion object {
        const val HISTORY = "history"
        fun newInstance(
            historyitems: ArrayList<TrackingDetail>
        ) = TrackingHistoryFragment().apply {
            arguments = Bundle().apply { putParcelableArrayList(HISTORY, historyitems) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding<BottomSheetDialogFragmentBinding>(
        inflater, R.layout.bottom_sheet_dialog_fragment,container
    ).apply {
        lifecycleOwner = viewLifecycleOwner
        this.trackingHistoryRv.adapter = TimeLineListAdapter().apply {

            val list = arguments?.getParcelableArrayList(HISTORY)?: ArrayList<TrackingDetail>()
            list.forEach{
                addHistory(it)
                addTimeLine(Timepoint("",""))
            }
        }
    }.root


}