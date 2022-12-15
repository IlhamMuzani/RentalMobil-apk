package com.anam.rentalmobil.data.model.produk

import com.google.gson.annotations.SerializedName

data class DataMobil (

    @SerializedName( "id") val id: Long?,
    @SerializedName("nama") val nama: String?,
    @SerializedName("tahun") val tahun: String?,
    @SerializedName("plat") val plat: String?,
    @SerializedName("warna") val warna: String,
    @SerializedName("kapasitas") val kapasitas: String,
    @SerializedName("fasilitas") val fasilitas: String,
    @SerializedName("gambar") val gambar: String,
    @SerializedName("status") val status: String,
)