package com.anam.rentalmobil.ui.fragment.fragment.detailproduk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.anam.rentalmobil.R
import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.Constant
import com.anam.rentalmobil.data.model.produk.DataProduk
import com.anam.rentalmobil.data.model.produk.ResponseProduk
import com.anam.rentalmobil.ui.booking.BookingActivity
import com.anam.rentalmobil.ui.fragment.UserActivity
import com.anam.rentalmobil.ui.login.LoginActivity
import com.anam.rentalmobil.ui.sweetalert.SweetAlertDialog
import com.anam.rentalmobil.ui.utils.GlideHelper
import kotlinx.android.synthetic.main.activity_detailproduk.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.NumberFormat
import java.util.*

class DetailprodukActivity : AppCompatActivity(), DetailprodukContract.View {

    lateinit var presenter: DetailprodukPresenter
    lateinit var produk: DataProduk
    lateinit var prefsManager: PrefsManager

    lateinit var sLoading: SweetAlertDialog
    lateinit var sError: SweetAlertDialog
    lateinit var sSuccess: SweetAlertDialog
    lateinit var sAlert: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailproduk)
        presenter = DetailprodukPresenter(this)
        prefsManager = PrefsManager(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.getDetailproduk(Constant.PRODUK_ID)
    }

    override fun initActivity() {
        tv_nama.text ="Detail Mobil"

        sLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal")
        sAlert = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Peringatan !")
    }

    override fun initListener() {
        ivKembali.setOnClickListener {
            onBackPressed()
        }

        btnbooking.setOnClickListener {
            if (prefsManager.prefIsLogin){
                Constant.PRODUK_ID = produk.id!!
                startActivity(Intent(this, BookingActivity::class.java))
            }else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }

    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> {
                sLoading.setContentText(message).show()
            }
            false -> {
                sLoading.dismiss()
            }
        }
    }

    override fun onResult(responseProduk: ResponseProduk) {
        produk = responseProduk.dataproduk!!

        GlideHelper.setImage( applicationContext,Constant.IP_IMAGE + produk.mobil.gambar!!, imvgambardetail)
        txvnama.text = produk.mobil.nama
        txvwarna.text = produk.mobil.warna
        txvtahun.text = produk.mobil.tahun
        txvkapasitas.text = produk.mobil.kapasitas
        txvfasilitas.text = produk.mobil.fasilitas
        txvplat.text = produk.mobil.plat
        id_sewa.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(Integer.valueOf(produk.sewa))
    }

    override fun showSuccessOK(message: String) {
        sSuccess
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismissWithAnimation()
                startActivity(Intent(this, UserActivity::class.java))
            }.show()
    }

    override fun showSuccess(message: String) {
        sSuccess
            .setContentText(message)
            .setTitleText("Ok")
            .setConfirmClickListener {
                it.dismissWithAnimation()
            }.show()
    }

    override fun showError(message: String) {
        sError
            .setContentText(message)
            .setConfirmText("Gagal")
            .setConfirmClickListener {
                it.dismiss()
            }.show()
    }

    override fun showAlert(message: String) {
        sAlert
            .setContentText(message)
            .setTitleText("Ya")
            .setConfirmClickListener {
                it.dismissWithAnimation()
            }
            .setTitleText("Nanti")
            .setConfirmClickListener {
                it.dismiss()
            }.show()
        sAlert.setCancelable(true)
    }
}