package com.jcy.trackingshipment.presentation.trackinghistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcy.trackingshipment.R
import com.jcy.trackingshipment.data.entity.TrackingDetail
import com.jcy.trackingshipment.databinding.ItemHistoryBinding
import com.jcy.trackingshipment.util.StringUtils


class TrackingHistoryListAdapter : ViewTypeDelegateAdapter {


    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        DataBindingUtil.inflate<ItemHistoryBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_history,
            parent,
            false
        ).let {
            ViewHolder(it)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as TrackingHistoryListAdapter.ViewHolder
        holder.bind(item as TrackingDetail)
    }

    inner class ViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(history: TrackingDetail){
            binding.trackingStatusTv.text = history.kind
            binding.trackingHistoryTime.text = history.timeString
            binding.trackingLocation.text = StringUtils.ShipmentWhereDescription(history.where.toString())
            binding.trackingCarriermanTv.text = StringUtils.ShipmentCarrierManDescription(history.manName.toString())
        }
    }
}