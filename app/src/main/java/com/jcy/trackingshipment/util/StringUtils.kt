package com.jcy.trackingshipment.util

class StringUtils{
    companion object{
        @JvmStatic
        fun ShipmentCarrierManDescription(manName: String): String{
            return "택배기사님 : $manName"
        }
        @JvmStatic
        fun ShipmentWhereDescription(manName: String): String{
            return "위치 : $manName"
        }
    }
}