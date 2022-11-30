package com.anam.rentalmobil.data.model.produk

import com.google.gson.annotations.SerializedName

data class ResponseProduk(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("produk") val dataproduk: DataProduk?
)