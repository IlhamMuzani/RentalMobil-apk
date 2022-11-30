package com.anam.rentalmobil.data.model.user

import com.google.gson.annotations.SerializedName

data class ResponseUser(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("user") val user: DataUser?
)