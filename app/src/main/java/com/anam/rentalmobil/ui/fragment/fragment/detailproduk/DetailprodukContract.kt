package com.anam.rentalmobil.ui.fragment.fragment.detailproduk

import com.anam.rentalmobil.data.model.produk.ResponseProduk

interface DetailprodukContract {

    interface Presenter {
        fun getDetailproduk(id: Long)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResult(responseProduk: ResponseProduk)
        fun showSuccessOK(message: String)
        fun showSuccess(message: String)
        fun showError(message: String)
        fun showAlert(message: String)
    }
}