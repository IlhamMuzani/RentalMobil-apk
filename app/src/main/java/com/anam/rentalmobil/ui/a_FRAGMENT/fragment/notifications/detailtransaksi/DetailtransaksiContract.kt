package com.anam.rentalmobil.ui.a_FRAGMENT.fragment.notifications.detailtransaksi

import com.anam.rentalmobil.data.model.rekening.DataRekening
import com.anam.rentalmobil.data.model.rekening.ResponseRekening
import com.anam.rentalmobil.data.model.transaksi.ResponseTransaksi
import com.anam.rentalmobil.data.model.transaksi.ResponseTransaksiUpdate
import java.io.File

interface DetailtransaksiContract {

    interface Presenter {
        fun getDetailtransaksi(id: Long)
        fun buktipembayaran(id: Long, bukti: File)
        fun gettampilrekening()
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onShowdialoguploadbukti()
        fun onShowDialogRek(dataRekening: List<DataRekening>)

        fun onResultTampilrekening(responseRekening: ResponseRekening)
        fun onResultUpdate(responseTransaksiUpdate: ResponseTransaksiUpdate)
        fun onResult(responseTransaksi: ResponseTransaksi)
        fun showMessage(message: String)
    }
}