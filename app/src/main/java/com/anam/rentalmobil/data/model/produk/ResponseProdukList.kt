package com.anam.rentalmobil.data.model.produk

import com.google.gson.annotations.SerializedName

data class ResponseProdukList(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("produks") val dataproduk: List<DataProduk>
)