package com.jcy.trackingshipment.extension

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import com.jcy.trackingshipment.TrackerApplication.Companion.appContext

/**
 * @author hongbeomi
 */

class ToastUtil {

    companion object {

        @JvmStatic
        fun showShort(@StringRes stringRes: Int, ctx: Context? = null) {
            val context = ctx ?: appContext
            Toast.makeText(context, stringRes, Toast.LENGTH_SHORT).show()
        }

        @JvmStatic
        fun showShort(string: String, ctx: Context? = null) {
            val context = ctx ?: appContext
            Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
        }

        @JvmStatic
        fun showLong(@StringRes stringRes: Int, ctx: Context? = null) {
            val context = ctx ?: appContext
            Toast.makeText(context, stringRes, Toast.LENGTH_LONG).show()
        }

        @JvmStatic
        fun showLong(string: String, ctx: Context? = null) {
            val context = ctx ?: appContext
            Toast.makeText(context, string, Toast.LENGTH_LONG).show()
        }

    }

}