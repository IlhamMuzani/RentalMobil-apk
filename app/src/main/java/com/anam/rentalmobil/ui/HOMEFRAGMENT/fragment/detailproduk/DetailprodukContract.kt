package com.anam.rentalmobil.ui.HOMEFRAGMENT.fragment.detailproduk

import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.produk.ResponseProduk
import com.anam.rentalmobil.data.model.user.DataUser
import com.anam.rentalmobil.data.model.user.ResponseUser

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