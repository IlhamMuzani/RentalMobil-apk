package com.anam.rentalmobil.data.model.Booking

import com.google.gson.annotations.SerializedName

data class ResponseBookingInsert(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String,
)