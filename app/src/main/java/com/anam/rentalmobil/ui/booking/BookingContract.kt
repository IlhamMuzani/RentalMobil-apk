package com.anam.rentalmobil.ui.booking

import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.Booking.ResponseBookingInsert
import com.anam.rentalmobil.data.model.produk.ResponseProduk
import com.anam.rentalmobil.data.model.produk.ResponseProdukList
import com.anam.rentalmobil.data.model.user.DataUser
import com.anam.rentalmobil.data.model.user.ResponseSopirList
import com.anam.rentalmobil.data.model.user.ResponseUser
import com.anam.rentalmobil.data.model.user.ResponseUserdetail

interface BookingContract {

    interface Presenter {
        fun inserBooking(pelanggan_id: String, produk_id: String, kategori: String, tanggal: String, lama: String, harga: String)
        fun inserBookingtrevel(pelanggan_id: String, produk_id: String, kategori: String, sopir_id: String, area: String, tanggal: String, lama: String, harga: String)
        fun getSopir()
        fun getDetailproduk(id: Long)

    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResult(responseBookingInsert: ResponseBookingInsert)
        fun onResultrevel(responseBookingInsert: ResponseBookingInsert)
        fun onResultSopir(responseSopirList: ResponseSopirList)
        fun onResultdetail(responseProduk: ResponseProduk)
        fun showMessage(message: String)
    }
}