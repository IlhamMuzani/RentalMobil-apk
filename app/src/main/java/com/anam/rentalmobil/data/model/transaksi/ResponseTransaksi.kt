package com.anam.rentalmobil.data.model.transaksi

import com.google.gson.annotations.SerializedName

data class ResponseTransaksi(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("transaksi") val dataTransaksi: DataTransaksi?
)