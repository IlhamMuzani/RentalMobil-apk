package com.anam.rentalmobil.data.model.transaksi

import com.anam.rentalmobil.data.model.produk.DataProduk
import com.anam.rentalmobil.data.model.user.DataUser
import com.google.gson.annotations.SerializedName

class DataTransaksi(

    @SerializedName("id") val id:Long?,
    @SerializedName("produk_id") val produk_id:String?,
    @SerializedName("pelanggan_id") val pelanggan_id:String?,
    @SerializedName("sopir_id") val sopir_id:String?,
    @SerializedName("tanggal") val tanggal:String?,
    @SerializedName("lama") val lama:String?,
    @SerializedName("harga") val harga:String?,
    @SerializedName("metode") val metode:String?,
    @SerializedName("bukti") val bukti:String?,
    @SerializedName("status") val status:String?,
    @SerializedName("pelanggan") val pelanggan: DataUser,
    @SerializedName("produk") val produk: DataProduk,
)