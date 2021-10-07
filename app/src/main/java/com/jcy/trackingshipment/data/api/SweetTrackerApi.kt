package com.jcy.trackingshipment.data.api


import com.jcy.trackingshipment.BuildConfig
import com.jcy.trackingshipment.data.entity.ShippingCompanies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SweetTrackerApi {

    //택배사 목록 조회
    @GET("/api/v1/companylist?t_key=${BuildConfig.SWEET_TRACKER_API_KEY}")
    suspend fun getCompanyLists() : Response<ShippingCompanies>

//    //운송장 번호 조회
//    @GET("/api/v1/trackingInfot_key=${BuildConfig.SWEET_TRACKER_API_KEY}")
//    suspend fun getTrackingApi(
//        @Query("t_code") companyCode: String,
//        @Query("t_invoice") invoice: String
//    ):Response<>
}