package com.anam.rentalmobil.ui.a_FRAGMENT.fragment.detailproduk

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
import com.anam.rentalmobil.ui.login.LoginActivity
import com.anam.rentalmobil.ui.utils.GlideHelper
import kotlinx.android.synthetic.main.activity_detailproduk.*
import kotlinx.android.synthetic.main.toolbar.*

class DetailprodukActivity : AppCompatActivity(), DetailprodukContract.View {

    lateinit var presenter: DetailprodukPresenter
    lateinit var produk: DataProduk
    lateinit var prefsManager: PrefsManager

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

    override fun onLoading(loading: Boolean) {
        when (loading) {
            true -> {
                progressdetail.visibility = View.VISIBLE
            }
            false -> {
                progressdetail.visibility = View.GONE
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
        id_sewa.text = produk.sewa
//        txvHarga.text = produk.sewa
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message,  Toast.LENGTH_SHORT).show()
    }
}