package com.anam.rentalmobil.data.model.transaksi

import com.google.gson.annotations.SerializedName

class ResponseTransaksiUpdate (
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String
)