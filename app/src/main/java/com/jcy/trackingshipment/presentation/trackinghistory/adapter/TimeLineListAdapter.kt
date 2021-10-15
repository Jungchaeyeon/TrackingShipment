package com.jcy.trackingshipment.presentation.trackinghistory.adapter

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView
import com.jcy.trackingshipment.data.entity.TrackingDetail
import com.jcy.trackingshipment.data.entity.model.Timepoint


class TimeLineListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()

    private var itemList: ArrayList<ViewType> = arrayListOf()
    init {
        delegateAdapters.put(ViewType.LINE, TimepointItemDelegateAdapter())
        delegateAdapters.put(ViewType.ITEM, TrackingHistoryListAdapter())
    }

    override fun getItemCount(): Int = itemList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType)?.onCreateViewHolder(parent)!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position))?.onBindViewHolder(holder, itemList[position])
    }

    override fun getItemViewType(position: Int) = itemList[position].getViewType()

    fun addHistory(item: TrackingDetail) {
        this.itemList.add(item)
        notifyDataSetChanged()
    }

    fun addTimeLine(item: Timepoint) {
        this.itemList.add(item)
        notifyDataSetChanged()
    }
}