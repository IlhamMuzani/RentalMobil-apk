package com.anam.rentalmobil.data.model.produk

import com.google.gson.annotations.SerializedName

data class DataMobil (

    @SerializedName( "id") val id: Long?,
    @SerializedName( "nama") val mobil_id: String?,
    @SerializedName( "tahun") val sewa: String?,
    @SerializedName("plat") val denda: String?,
    @SerializedName("warna") val warna: String,
    @SerializedName("kapasitas") val kapasitas: String,
    @SerializedName("fasilitas") val fasilitas: String,
    @SerializedName("gambar") val gambar: String,
    @SerializedName("status") val status: String,
    @SerializedName("mobil") val mobil: DataMobil,
)