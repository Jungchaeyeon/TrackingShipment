package com.jcy.trackingshipment.data.repository

import android.util.Log
import com.jcy.trackingshipment.data.api.SweetTrackerApi
import com.jcy.trackingshipment.data.db.ShippingCompanyDao
import com.jcy.trackingshipment.data.entity.ShippingCompany
import com.jcy.trackingshipment.data.preference.PreferenceManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ShippingCompanyRepositoryImpl(
    private val trackerApi: SweetTrackerApi,
    private val shippingCompanyDao: ShippingCompanyDao,
    private val dispatcher: CoroutineDispatcher,
    private val preferenceManager: PreferenceManager,
): ShippingCompanyRepository{

    override suspend fun getShippingCompananies(): List<ShippingCompany> = withContext(dispatcher) {

//        val currentTimeMillis = System.currentTimeMillis()
//        val lastDatabaseUpdatedTimeMillis = preferenceManager.getLong(KEY_LAST_DATABASE_UPDATED_TIME_MILLIS)
//
//        if(lastDatabaseUpdatedTimeMillis == null ||
//                CACHE_MAX_AGE_MILLIS < (currentTimeMillis - lastDatabaseUpdatedTimeMillis))
//                {
                    val shippingCompanies = trackerApi.getCompanyLists()
                        .body()
                        ?.shippingCompanies
                        ?: emptyList()
                    //shippingCompanyDao.insert(shippingCompanies)
                   // preferenceManager.putLong(KEY_LAST_DATABASE_UPDATED_TIME_MILLIS, currentTimeMillis)

        Log.e("택배회사목록",shippingCompanies.toString())
        //}
        shippingCompanyDao.getAll()
    }
    companion object {
        private const val KEY_LAST_DATABASE_UPDATED_TIME_MILLIS = "KEY_LAST_DATABASE_UPDATED_TIME_MILLIS"
        private const val CACHE_MAX_AGE_MILLIS = 1000L * 60 * 60 * 24 * 7
    }

}