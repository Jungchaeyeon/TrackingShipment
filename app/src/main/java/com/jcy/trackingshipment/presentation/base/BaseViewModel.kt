package com.jcy.trackingshipment.presentation.base

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jcy.trackingshipment.R
import com.jcy.trackingshipment.extension.ToastUtil.Companion.showShort
import com.jcy.trackingshipment.util.NoConnectionInterceptor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import kotlin.coroutines.coroutineContext

abstract class BaseViewModel: ViewModel() {
    protected var stateBundle: Bundle? = null

    open fun fetchData(): Job = viewModelScope.launch {}

    open fun storeState(stateBundle: Bundle){
        this.stateBundle = stateBundle
    }
    suspend fun <T> handle(call: suspend () -> T): T? {
        return withContext(CoroutineScope(coroutineContext).coroutineContext) {
            call.runCatching { this.invoke() }
                .getOrElse {
                    onError(it)
                    null
                }
        }
    }
    open fun onError(t: Throwable) {
        viewModelScope.launch {
            when (t) {
                is NoConnectionInterceptor.NoConnectivityException ->
                    showShort(R.string.base_error_no_connect_network)
                is HttpException -> showShort(R.string.base_error_https)
                else -> {
                    showShort(R.string.base_error_unknown)
                    Log.e("ERROR", "${t.message}")
                }
            }
        }
    }
}