package com.anam.rentalmobil.ui.a_FRAGMENT.fragment.detailproduk

import com.anam.rentalmobil.data.model.produk.ResponseProduk

interface DetailprodukContract {

    interface Presenter {
        fun getDetailproduk(id: Long)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResult(responseProduk: ResponseProduk)
        fun showMessage(message: String)
    }
}