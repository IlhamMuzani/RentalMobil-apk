package com.anam.rentalmobil.data.model.rekening

import com.google.gson.annotations.SerializedName


data class ResponseRekening (

    @SerializedName("status") val status : Boolean,
    @SerializedName("message") val msg : String,
    @SerializedName("rekenings") val datarekening : List<DataRekening>
)