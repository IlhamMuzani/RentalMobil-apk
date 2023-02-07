package com.anam.rentalmobil.ui.fragment.fragment.notifications.detailtransaksi

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.anam.rentalmobil.R
import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.Constant
import com.anam.rentalmobil.data.model.rekening.DataRekening
import com.anam.rentalmobil.data.model.rekening.ResponseRekening
import com.anam.rentalmobil.data.model.transaksi.DataTransaksi
import com.anam.rentalmobil.data.model.transaksi.ResponseTransaksi
import com.anam.rentalmobil.data.model.transaksi.ResponseTransaksiUpdate
import com.anam.rentalmobil.ui.fragment.UserActivity
import com.anam.rentalmobil.ui.invoice.InvoiceActivity
import com.anam.rentalmobil.ui.sweetalert.SweetAlertDialog
import com.anam.rentalmobil.ui.utils.FileUtils
import com.anam.rentalmobil.ui.utils.GlideHelper
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lazday.poslaravel.util.GalleryHelper
import kotlinx.android.synthetic.main.activity_detailproduk.*
import kotlinx.android.synthetic.main.activity_detailproduk.imvgambardetail
import kotlinx.android.synthetic.main.activity_detailtransaksi.*
import kotlinx.android.synthetic.main.dialog_bukti.view.*
import kotlinx.android.synthetic.main.dialog_rekening.view.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.NumberFormat
import java.util.*

class DetailtransaksiActivity : AppCompatActivity(), DetailtransaksiContract.View {

    lateinit var presenter: DetailtransaksiPresenter
    lateinit var transaksi: DataTransaksi
    lateinit var rekening: DataRekening
    lateinit var rekeningAdapter: RekeningAdapter
    lateinit var prefsManager: PrefsManager

    private var uriImage: Uri? = null
    private var pickImage = 1
    lateinit var imvBukti: ImageView

    private val STORAGE_CADE = 1001

    private lateinit var sLoading: SweetAlertDialog
    private lateinit var sSuccess: SweetAlertDialog
    private lateinit var sError: SweetAlertDialog
    private lateinit var sAlert: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailtransaksi)
        presenter = DetailtransaksiPresenter(this)
        prefsManager = PrefsManager(this)

    }

    override fun onStart() {
        super.onStart()
        presenter.getDetailtransaksi(Constant.TRANSAKSI_ID)
    }

    override fun initActivity() {

        sLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal !")
        sAlert = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Peringatan !")

        tv_nama.text ="Detail Transaksi"

        rekeningAdapter = RekeningAdapter(
            this,
            arrayListOf()
        ) { datarekening: DataRekening, position: Int, type: String ->
            Constant.REKENING_ID = datarekening.id!!

            rekening = datarekening

        }

        btnuploadbukti.setOnClickListener {
            onShowdialoguploadbukti()
        }
    }

    override fun initListener() {
        ivKembali.setOnClickListener {
            onBackPressed()
        }

        btninvoice.setOnClickListener {
            presenter.getDetailtransaksi(Constant.TRANSAKSI_ID)
            startActivity(Intent(this, InvoiceActivity::class.java))
        }

        imvbukti.setOnClickListener {
            Constant.USER_ID = transaksi.id!!
            onShowDialogdetailgambar()
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

    override fun onShowdialoguploadbukti() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_bukti, null)

        view.txtHargadialog.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(Integer.valueOf(transaksi.harga))

        imvBukti = view.imvBuktipembayaran

        view.layout_rekeninge3.setOnClickListener {
            presenter.gettampilrekening()
        }

        view.imvBuktipembayaran.setOnClickListener {
            if (GalleryHelper.permissionGallery(this, this, pickImage)) {
                GalleryHelper.openGallery(this)
            }
        }

        view.btnKirimPelunasan.setOnClickListener {

            if (uriImage == null) {
               showError("masukkan Bukti Pembayaran !")
            } else {
                presenter.buktipembayaran(transaksi.id!!, FileUtils.getFile(this, uriImage))

                dialog.dismiss()
                finish()
            }
        }

        dialog.setContentView(view)
        dialog.show()
    }

    override fun onShowDialogRek(dataRekening: List<DataRekening>) {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_rekening, null)

        rekeningAdapter.setData(dataRekening)

        view.layout_rekening.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rekeningAdapter
        }

        dialog.setContentView(view)
        dialog.show()
    }

    override fun onShowDialogdetailgambar() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_gambar, null)
        val imvgambar = view.findViewById<ImageView>(R.id.imvgambar)

        GlideHelper.setImage(this, Constant.IP_IMAGE + transaksi.bukti, imvgambar)

        dialog.setContentView(view)
        dialog.show()
    }

    override fun onResultTampilrekening(responseRekening: ResponseRekening) {
        val datarekening: List<DataRekening> = responseRekening.datarekening

        onShowDialogRek(datarekening)
    }

    override fun onResultUpdate(responseTransaksiUpdate: ResponseTransaksiUpdate) {
        Toast.makeText(applicationContext, "Berhasil Mengirim Bukti", Toast.LENGTH_SHORT).show()
    }

    override fun onResult(responseTransaksi: ResponseTransaksi) {
        transaksi = responseTransaksi.dataTransaksi!!

        GlideHelper.setImage( applicationContext,Constant.IP_IMAGE + transaksi.produk.mobil.gambar!!, imvgambardetail)
        txvnamamobil.text = transaksi.produk.mobil.nama
        txvwarnamobil.text = transaksi.produk.mobil.warna
        txvtahunmobil.text = transaksi.produk.mobil.tahun
        txvkapasitasmobil.text = transaksi.produk.mobil.kapasitas
        txvfasilitasmobil.text = transaksi.produk.mobil.fasilitas
        txvplatmobil.text = transaksi.produk.mobil.plat
        txvhargamobil.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(Integer.valueOf(transaksi.produk.sewa))

        txvnamapesanan.text = transaksi.pelanggan.nama
        txvtanggalpemesanan.text = transaksi.tanggal
        txvlamapesanan.text = transaksi.lama
        txvhargasewa.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(Integer.valueOf(transaksi.harga))

        if (transaksi.produk.kategori == "tour") {
            layoutkategoritrans.visibility = View.VISIBLE
            if (transaksi.produk.area == "luar") {
                txvareadalamkota.visibility = View.VISIBLE
                txvarealuarkota.visibility = View.GONE
            } else {
                txvareadalamkota.visibility = View.GONE
                txvarealuarkota.visibility = View.VISIBLE
            }
        } else {
            layoutkategoritrans.visibility = View.GONE
        }

        if(transaksi.bukti.isNullOrEmpty()){
                layout_bukti.visibility = View.GONE
                btninvoice.visibility = View.GONE
        }else if (transaksi.bukti!!.isNotEmpty() && transaksi.status == "menunggu"){
            layout_bukti.visibility = View.GONE
            btninvoice.visibility = View.GONE
            btnuploadbukti.visibility = View.GONE
        }else if (transaksi.bukti!!.isNotEmpty() && transaksi.status == "proses"){
                layout_bukti.visibility = View.VISIBLE
                btnuploadbukti.visibility = View.GONE
                GlideHelper.setImage( applicationContext,Constant.IP_IMAGE + transaksi.bukti!!, imvbukti)
        }else if (transaksi.bukti!!.isNotEmpty() && transaksi.status == "selesai"){
            btninvoice.visibility = View.GONE
            btnuploadbukti.visibility = View.GONE
            GlideHelper.setImage( applicationContext,Constant.IP_IMAGE + transaksi.bukti!!, imvbukti)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage && resultCode == Activity.RESULT_OK) {
            uriImage = data!!.data
            imvBukti.setImageURI(uriImage)
        }
    }

    override fun showSuccesOk(message: String) {
        sSuccess
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismissWithAnimation()
                finish()
                startActivity(Intent(this, UserActivity::class.java))
            }
            .show()
    }

    override fun showSucces(message: String) {
        sSuccess
            .setContentText(message)
            .setConfirmText("Ok")
            .setConfirmClickListener {
                it.dismissWithAnimation()
            }.show()
    }

    override fun showError(message: String) {
        sError
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismiss()
            }.show()
    }

    override fun showAlert(message: String) {
        sAlert
            .setContentText(message)
            .setConfirmText("Ya")
            .setConfirmClickListener {
                it.dismissWithAnimation()
            }
            .setConfirmText("Nanti")
            .setConfirmClickListener {
                it.dismiss()
            }.show()
        sAlert.setCancelable(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }


}