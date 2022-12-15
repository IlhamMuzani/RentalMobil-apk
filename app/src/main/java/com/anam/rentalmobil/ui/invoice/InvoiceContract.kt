package com.anam.rentalmobil.ui.invoice

import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.transaksi.ResponseTransaksi
import com.anam.rentalmobil.data.model.user.DataUser
import com.anam.rentalmobil.data.model.user.ResponseUser

interface InvoiceContract {

    interface Presenter {
        fun getDetail(id: Long)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResult(responseTransaksi: ResponseTransaksi)
        fun showMessage(message: String)
    }
}