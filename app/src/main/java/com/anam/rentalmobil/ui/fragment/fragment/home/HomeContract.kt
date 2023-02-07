package com.anam.rentalmobil.ui.fragment.fragment.home

import com.anam.rentalmobil.data.model.produk.ResponseProdukList


interface HomeContract {

    interface Presenter{
        fun getProduk()
        fun Searchproduk(keyword: String, kategori: String)
    }

    interface View{
        fun initFragment(view: android.view.View)
        fun onLoading(loading: Boolean)
        fun onLoadingswet(loading: Boolean, message: String? = "Menampilkan..")
        fun onResult(responseProdukList: ResponseProdukList)
        fun showSuccesOk(message: String)
        fun showSucces(message: String)
        fun showError(message: String)
        fun showAlert(message: String)
    }
}