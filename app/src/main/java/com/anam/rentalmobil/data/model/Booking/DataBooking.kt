package com.anam.rentalmobil.data.model.Booking

import com.anam.rentalmobil.data.model.produk.DataProduk
import com.google.gson.annotations.SerializedName

data class DataBooking (

    @SerializedName("id") val id: Long?,
    @SerializedName("pelanggan_id") val pelanggan_id: String?,
    @SerializedName("produk_id") val produk_id: String?,
    @SerializedName("kategori") val kategori: String?,
    @SerializedName("sopir_id") val sopir: String,
    @SerializedName("area") val area: String,
    @SerializedName("tanggal") val tanggal: String,
    @SerializedName("lama") val lama: String,
    @SerializedName("harga") val harga: String,
    @SerializedName("produk") val produk: DataProduk,
    )