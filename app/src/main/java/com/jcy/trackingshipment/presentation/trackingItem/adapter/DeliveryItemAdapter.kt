package com.jcy.trackingshipment.presentation.trackingItem.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jcy.trackingshipment.R
import com.jcy.trackingshipment.data.entity.TrackingDetail
import com.jcy.trackingshipment.data.entity.model.Delivery
import com.jcy.trackingshipment.databinding.ItemTrackingBinding
import java.util.*
import kotlin.collections.ArrayList

class DeliveryItemAdapter(
    private val activity: Activity,
    private val itemClick: (List<TrackingDetail>) -> Unit,
    private val itemDelete: (Delivery) -> Unit

): RecyclerView.Adapter<DeliveryItemAdapter.DeliveryItemViewHolder>() {

    private var itemList: List<Delivery> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = DataBindingUtil.inflate<ItemTrackingBinding>(
        LayoutInflater.from(parent.context),
        R.layout.item_tracking,
        parent,
        false
    ).let{
        DeliveryItemViewHolder(it)
    }

    override fun onBindViewHolder(holder: DeliveryItemAdapter.DeliveryItemViewHolder, position: Int) {
        return (holder as DeliveryItemViewHolder).bind(itemList[position])
    }

    override fun getItemCount() = itemList.size

    fun setItemList(itemList: List<Delivery>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    inner class DeliveryItemViewHolder(private val binding: ItemTrackingBinding) :
            RecyclerView.ViewHolder(binding.root){
                fun bind(delivery: Delivery?){
                    binding.delivery = delivery

                    binding.deleteBtnLayout.setOnClickListener {
                        itemDelete(delivery!!)
                    }
                    binding.itemContainer.setOnClickListener {
                        binding.swipeLayout.close()
                        if(!binding.deleteBtnLayout.isShown){
                            delivery?.let { deliveryItem -> itemClick(deliveryItem.trackingHistorys) }
                        }
                    }

                }

            }
}