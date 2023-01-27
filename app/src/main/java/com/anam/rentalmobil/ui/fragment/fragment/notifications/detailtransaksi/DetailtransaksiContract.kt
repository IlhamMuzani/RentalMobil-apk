package com.anam.rentalmobil.ui.fragment.fragment.notifications.detailtransaksi

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
        fun onLoading(loading: Boolean, message: String? ="Loading..")
        fun onShowdialoguploadbukti()
        fun onShowDialogRek(dataRekening: List<DataRekening>)
        fun onShowDialogdetailgambar()
        fun onResultTampilrekening(responseRekening: ResponseRekening)
        fun onResultUpdate(responseTransaksiUpdate: ResponseTransaksiUpdate)
        fun onResult(responseTransaksi: ResponseTransaksi)
        fun showSuccesOk(message: String)
        fun showSucces(message: String)
        fun showError(message: String)
        fun showAlert(message: String)
    }
}