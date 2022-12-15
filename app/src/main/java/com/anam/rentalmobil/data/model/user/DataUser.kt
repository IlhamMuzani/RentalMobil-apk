package com.anam.rentalmobil.data.model.user

import com.google.gson.annotations.SerializedName

class DataUser (
    @SerializedName( "id") val id: String?,
    @SerializedName( "nik") val nik: String?,
    @SerializedName( "nama") val nama: String?,
    @SerializedName("telp") val telp: String?,
    @SerializedName("gender") val gender: String?,
    @SerializedName("latitude") val latitude: String?,
    @SerializedName("longitude") val longitude: String?,
    @SerializedName("alamat") val alamat: String?,
    @SerializedName("foto") val foto: String?,
    @SerializedName("role") val role: String?
)