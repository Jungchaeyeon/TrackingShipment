package com.jcy.trackingshipment.extension

import androidx.lifecycle.*

fun <T> MediatorLiveData<T>.addSourceList(vararg liveDataList: MutableLiveData<*>, onChanged: () -> T) {
    liveDataList.forEach {
        this.addSource(it) { value = onChanged() }
    }
}


inline fun <T> LiveData<T>.observeOnce(
    lifecycleOwner: LifecycleOwner,
    crossinline observer: (T) -> Unit
) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T) {
            observer.invoke(t)
            removeObserver(this)
        }
    })
}