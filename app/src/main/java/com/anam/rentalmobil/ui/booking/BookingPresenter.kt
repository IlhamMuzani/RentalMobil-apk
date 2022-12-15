package com.anam.rentalmobil.ui.booking

import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.Booking.ResponseBookingInsert
import com.anam.rentalmobil.data.model.produk.ResponseProduk
import com.anam.rentalmobil.data.model.produk.ResponseProdukList
import com.anam.rentalmobil.data.model.user.DataUser
import com.anam.rentalmobil.data.model.user.ResponseSopirList
import com.anam.rentalmobil.data.model.user.ResponseUser
import com.anam.rentalmobil.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingPresenter (val view: BookingContract.View) : BookingContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)
    }

    override fun inserBooking(
        pelanggan_id: String,
        produk_id: String,
        tanggal: String,
        lama: String,
        harga: String
    ) {
        view.onLoading(true)
        ApiService.endpoint.insertBooking(pelanggan_id, produk_id, tanggal, lama, harga).enqueue(object: Callback<ResponseBookingInsert>{
            override fun onResponse(
                call: Call<ResponseBookingInsert>,
                response: Response<ResponseBookingInsert>
            ) {
                view.onLoading(true)
                if (response.isSuccessful){
                    val responseBookingInsert: ResponseBookingInsert? = response.body()
                    view.onResult(responseBookingInsert!!)
                }
            }

            override fun onFailure(call: Call<ResponseBookingInsert>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

//    override fun inserBookingtrevel(
//        pelanggan_id: String,
//        produk_id: String,
//        kategori: String,
//        sopir_id: String,
//        area: String,
//        tanggal: String,
//        lama: String,
//        harga: String
//    ) {
//        view.onLoading(true)
//        ApiService.endpoint.insertBookingtrevel(pelanggan_id, produk_id, kategori, sopir_id, area, tanggal, lama, harga).enqueue(object  : Callback<ResponseBookingInsert>{
//            override fun onResponse(
//                call: Call<ResponseBookingInsert>,
//                response: Response<ResponseBookingInsert>
//            ) {
//                view.onLoading(true)
//                if (response.isSuccessful){
//                    val responseBookingInsert: ResponseBookingInsert? = response.body()
//                    view.onResultrevel(responseBookingInsert!!)
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseBookingInsert>, t: Throwable) {
//                view.onLoading(false)
//            }
//
//        })
//    }

//    override fun getSopir() {
//        view.onLoading(false)
//        ApiService.endpoint.getsopir().enqueue(object: Callback<ResponseSopirList>{
//            override fun onResponse(
//                call: Call<ResponseSopirList>,
//                response: Response<ResponseSopirList>
//            ) {
//                view.onLoading(false)
//                if (response.isSuccessful){
//                    val responseSopirList: ResponseSopirList? = response.body()
//                    view.onResultSopir(responseSopirList!!)
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseSopirList>, t: Throwable) {
//                view.onLoading(false)
//            }
//
//        })
//    }

    override fun getDetailproduk(id: Long) {
        view.onLoading(true)
        ApiService.endpoint.getDetailproduk(id).enqueue(object : Callback<ResponseProduk>{
            override fun onResponse(
                call: Call<ResponseProduk>,
                response: Response<ResponseProduk>
            ) {
                view.onLoading(false)
                if (response.isSuccessful){
                    val responseProduk:ResponseProduk? = response.body()
                    view.onResultdetail(responseProduk!!)
                }
            }

            override fun onFailure(call: Call<ResponseProduk>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }
}