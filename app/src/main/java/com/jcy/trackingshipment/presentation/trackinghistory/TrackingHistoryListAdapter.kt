package com.jcy.trackingshipment.presentation.trackinghistory

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcy.trackingshipment.R
import com.jcy.trackingshipment.data.entity.TrackingDetail
import com.jcy.trackingshipment.databinding.ItemHistoryBinding
import com.jcy.trackingshipment.util.StringUtils


class TrackingHistoryListAdapter : RecyclerView.Adapter<TrackingHistoryListAdapter.TimeLineViewHolder>() {

    private var itemList: ArrayList<TrackingDetail> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataBindingUtil.inflate<ItemHistoryBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_history,
            parent,
            false
        ).let { TimeLineViewHolder(it, viewType) }

    override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) =
        holder.bind(itemList[itemList.size.minus(1) - position])

    override fun getItemCount() = itemList.size

    fun setItemList(itemList: ArrayList<TrackingDetail>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    inner class TimeLineViewHolder(private val binding: ItemHistoryBinding, viewType: Int) :
        RecyclerView.ViewHolder(binding.root) {


        @SuppressLint("SetTextI18n")
        fun bind(history: TrackingDetail) {
            binding.trackingStatusTv.text = history.kind
            binding.trackingHistoryTime.text = history.timeString
            binding.trackingLocation.text = StringUtils.ShipmentWhereDescription(history.where.toString())
            binding.trackingCarriermanTv.text = StringUtils.ShipmentCarrierManDescription(history.manName.toString())
        }
    }

}