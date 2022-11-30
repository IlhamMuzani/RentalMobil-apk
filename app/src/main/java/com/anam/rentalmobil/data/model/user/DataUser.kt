package com.anam.rentalmobil.data.model.user

import com.google.gson.annotations.SerializedName

class DataUser (
    @SerializedName( "id") val id: String?,
    @SerializedName( "nik") val nik: String?,
    @SerializedName( "nama") val nama: String?,
    @SerializedName("telp") val telp: String?,
    @SerializedName("alamat") val alamat: String?,
    @SerializedName("gender") val gender: String?,
    @SerializedName("foto") val foto: String?,
    @SerializedName("role") val role: String?
)