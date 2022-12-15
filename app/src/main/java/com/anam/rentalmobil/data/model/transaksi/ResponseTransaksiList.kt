package com.anam.rentalmobil.data.model.transaksi

import com.google.gson.annotations.SerializedName

data class ResponseTransaksiList(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("transaksis") val dataTransaksi: List<DataTransaksi>
)