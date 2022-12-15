package com.anam.rentalmobil.network

import com.anam.rentalmobil.data.model.Booking.ResponseBookingInsert
import com.anam.rentalmobil.data.model.produk.ResponseProduk
import com.anam.rentalmobil.data.model.produk.ResponseProdukList
import com.anam.rentalmobil.data.model.rekening.ResponseRekening
import com.anam.rentalmobil.data.model.transaksi.ResponseTransaksi
import com.anam.rentalmobil.data.model.transaksi.ResponseTransaksiList
import com.anam.rentalmobil.data.model.transaksi.ResponseTransaksiUpdate
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
        @Field("gender") gender: String,
        @Field("latitude") latitude: String,
        @Field("longitude") longitude: String,
        @Field("alamat") alamat: String,
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

    @Multipart
    @POST("user/u_profile/{id}")
    fun Updateuser(
        @Path("id") id: Long,
        @Query("nik") nik: String,
        @Query("nama") nama: String,
        @Query("telp") telp: String,
        @Query("gender") gender: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("alamat") alamat: String,
        @Part foto: MultipartBody.Part,
    ): Call<ResponseUser>

    @POST("user/u_password/{id}")
    fun Passwordbaru(
        @Path("id") id: Long,
        @Query("password") password: String,
        @Query("password_confirmation") password_confirmation: String,
    ): Call<ResponseUser>

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

    @POST("produk-search")
    fun Searchproduk(
        @Query("keyword") keyword: String,
        @Query("kategori") kategori: String
    ): Call<ResponseProdukList>

    @POST("transaksi-store")
    fun insertBooking(
        @Query("pelanggan_id") pelanggan_id: String,
        @Query("produk_id") produk_id: String,
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

    @GET("transaksi-belumbayar/{kd_user}")
    fun getTransaksimenunggu(
        @Path("kd_user") kd_user: Long
    ): Call<ResponseTransaksiList>

    @GET("transaksi-sudahbayar/{kd_user}")
    fun getTransaksisudahbayar(
        @Path("kd_user") kd_user: Long
    ): Call<ResponseTransaksiList>

    @DELETE("transaksi/{id}")
    fun deletetransaksi(
        @Path("id") id: Long
    ): Call<ResponseTransaksiUpdate>

    @GET("transaksi-detail/{id}")
    fun detailtransaksi(
        @Path("id") id: Long
    ): Call<ResponseTransaksi>

    @Multipart
    @POST("transaksi-upload/{id}")
    fun buktiPembayaran(
        @Path("id") id: Long,
        @Part bukti: MultipartBody.Part,
    ): Call<ResponseTransaksiUpdate>

    @GET("rekening-list")
    fun rekening(
    ): Call<ResponseRekening>

}