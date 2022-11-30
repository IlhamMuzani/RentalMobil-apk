package com.anam.rentalmobil.data.model.user

import com.google.gson.annotations.SerializedName

data class ResponseSopirList(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("users") val datasopir: List<DataUser>
)