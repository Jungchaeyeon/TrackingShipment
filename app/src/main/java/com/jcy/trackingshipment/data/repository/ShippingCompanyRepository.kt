package com.jcy.trackingshipment.data.repository

import com.jcy.trackingshipment.data.entity.ShippingCompany

interface ShippingCompanyRepository{

    suspend fun getShippingCompananies():List<ShippingCompany>
}