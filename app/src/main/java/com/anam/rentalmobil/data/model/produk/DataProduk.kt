package com.anam.rentalmobil.data.model.produk

import com.google.gson.annotations.SerializedName

data class DataProduk (

    @SerializedName("id") val id: Long?,
    @SerializedName("mobil_id") val mobil_id: String?,
    @SerializedName("kategori") val kategori: String?,
    @SerializedName("area") val area: String?,
    @SerializedName("sewa") val sewa: String,
    @SerializedName("mobil") val mobil: DataMobil,
    )