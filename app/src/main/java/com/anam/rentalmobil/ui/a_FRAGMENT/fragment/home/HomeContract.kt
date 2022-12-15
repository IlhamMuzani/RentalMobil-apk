package com.ilham.taspesialisbangunan.ui.home.fragment.home

import com.anam.rentalmobil.data.model.produk.ResponseProdukList


interface HomeContract {

    interface Presenter{
        fun getProduk()
        fun Searchproduk(keyword: String, kategori: String)
    }

    interface View{
        fun initFragment(view: android.view.View)
        fun onLoading(loading: Boolean)
        fun onResult(responseProdukList: ResponseProdukList)
        fun showMessage(message: String)
    }
}