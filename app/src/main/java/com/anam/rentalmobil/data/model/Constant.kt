package com.anam.rentalmobil.data.model

class Constant {
    companion object {

//        var IP: String = "http://192.168.1.43/travel-new/"
        var IP: String = "https://travel.ufomediategal.com/"
        var IP_IMAGE: String = IP + "public/storage/uploads/"

        var LATITUDE: String = ""
        var LONGITUDE: String = ""

        var AREA: String = ""
        var KEYWORD: String = ""

        const val LOCATION_PERMISSION_REQUEST_CODE = 1;

        var UPDATE: Boolean = false

        var USER_ID: Long = 0
        var PRODUK_ID: Long = 0

        var TRANSAKSI_ID: Long = 0
        var PRODUK_NAME: String = ""

        var REKENING_ID: Long = 0
        var REKENING_NAME: String = ""


        var KATEGORI_ID: Int = 0
        var KATEGORI_NAME: String = ""

        var AREA_ID: Int = 0
        var AREA_NAME: String = ""

        var SOPIR_ID: Int = 0
        var SOPIR_NAME: String = ""

        var LAMA_ID: Int = 0
        var LAMA_NAME: String = ""

        var IS_CHANGED: Boolean = false
    }
}