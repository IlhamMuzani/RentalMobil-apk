package com.anam.rentalmobil.ui.a_FRAGMENT.fragment.notifications.tabs.menunggu

import com.anam.rentalmobil.data.model.transaksi.DataTransaksi
import com.anam.rentalmobil.data.model.transaksi.ResponseTransaksiList
import com.anam.rentalmobil.data.model.transaksi.ResponseTransaksiUpdate

interface MenungguContract {

    interface Presenter {
        fun getStatusmenunggu(kd_user : Long)
        fun deletetransaksi(id: Long)
    }

    interface View{
        fun initFragment(view: android.view.View)
        fun onloading(loading: Boolean)
        fun onResult(responseTransaksiList: ResponseTransaksiList)
        fun showMessage(message: String)
        fun onResultDelete(responseTransaksiUpdate: ResponseTransaksiUpdate)
        fun showDialogDelete(dataTransaksi: DataTransaksi, position: Int)
    }
}