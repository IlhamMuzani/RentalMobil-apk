package com.anam.rentalmobil.network

import com.anam.rentalmobil.data.model.Booking.ResponseBookingInsert
import com.anam.rentalmobil.data.model.produk.ResponseProduk
import com.anam.rentalmobil.data.model.produk.ResponseProdukList
import com.anam.rentalmobil.data.model.user.ResponseSopirList
import com.anam.rentalmobil.data.model.user.ResponseUser
import com.anam.rentalmobil.data.model.user.ResponseUserdetail
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiEndpoint {

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("nik") nik: String,
        @Field("nama") nama: String,
        @Field("telp") telp: String,
        @Field("alamat") alamat: String,
        @Field("gender") gender: String,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String,
//        @Field("fcm") fcm: String
    ): Call<ResponseUser>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("telp") telp: String,
        @Field("password") password: String
    ) : Call<ResponseUser>

    @GET("user-detail/{id}")
    fun ProfilDetail(
        @Path("id") id: String
    ) : Call<ResponseUserdetail>

    @GET("produk-list")
    fun getproduk(
    ): Call<ResponseProdukList>

    @GET("user-sopir")
    fun getsopir(
    ): Call<ResponseSopirList>


    @GET("produk-detail/{id}")
    fun getDetailproduk(
        @Path("id") id: Long
    ): Call<ResponseProduk>

    @GET("produk-search")
    fun search(
        @Query("keyword") keyword: String
    ): Call<ResponseProdukList>

    @POST("transaksi-store")
    fun insertBooking(
        @Query("pelanggan_id") pelanggan_id: String,
        @Query("produk_id") produk_id: String,
        @Query("kategori") kategori: String,
        @Query("tanggal") tanggal: String,
        @Query("lama") lama: String,
        @Query("harga") harga: String,
    ): Call<ResponseBookingInsert>

    @POST("transaksi-store")
    fun insertBookingtrevel(
        @Query("pelanggan_id") pelanggan_id: String,
        @Query("produk_id") produk_id: String,
        @Query("kategori") kategori: String,
        @Query("sopir_id") sopir_id: String,
        @Query("area") area: String,
        @Query("tanggal") tanggal: String,
        @Query("lama") lama: String,
        @Query("harga") harga: String,
    ): Call<ResponseBookingInsert>
}