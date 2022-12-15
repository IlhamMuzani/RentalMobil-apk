package com.anam.rentalmobil.data.model.rekening

import com.google.gson.annotations.SerializedName

data class DataRekening (

    @SerializedName( "id") val id: Long?,
    @SerializedName( "bank") val bank: String?,
    @SerializedName( "nama") val nama: String?,
    @SerializedName( "nomor") val nomor: String?,
    @SerializedName( "gambar") val gambar: String?
    )