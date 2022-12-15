package com.anam.rentalmobil.ui.a_FRAGMENT.fragment.notifications.detailtransaksi

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
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
import com.anam.rentalmobil.ui.invoice.InvoiceActivity
import com.anam.rentalmobil.ui.utils.FileUtils
import com.anam.rentalmobil.ui.utils.GlideHelper
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lazday.poslaravel.util.GalleryHelper
import kotlinx.android.synthetic.main.activity_detailproduk.imvgambardetail
import kotlinx.android.synthetic.main.activity_detailproduk.progressdetail
import kotlinx.android.synthetic.main.activity_detailtransaksi.*
import kotlinx.android.synthetic.main.dialog_bukti.view.*
import kotlinx.android.synthetic.main.dialog_rekening.view.*
import kotlinx.android.synthetic.main.toolbar.*
import java.io.FileOutputStream
import java.text.SimpleDateFormat
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
            presenter.getDetailtransaksi(Constant.USER_ID)
            startActivity(Intent(this, InvoiceActivity::class.java))
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

    override fun onShowdialoguploadbukti() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_bukti, null)
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
                Toast.makeText(applicationContext, "Masukan bukti pembayaran", Toast.LENGTH_SHORT)
                    .show()
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
        txvhargamobil.text = transaksi.produk.sewa

        txvnamapesanan.text = transaksi.pelanggan.nama
        txvtanggalpemesanan.text = transaksi.tanggal
        txvlamapesanan.text = transaksi.lama
        txvhargasewa.text = transaksi.harga

        if(transaksi.bukti.isNullOrEmpty()){
                layout_bukti.visibility = View.GONE
                btninvoice.visibility = View.GONE
        }else{
                layout_bukti.visibility = View.VISIBLE
                btnuploadbukti.visibility = View.GONE
                btninvoice.visibility = View.VISIBLE
                GlideHelper.setImage( applicationContext,Constant.IP_IMAGE + transaksi.bukti!!, imvbukti)
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message,  Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage && resultCode == Activity.RESULT_OK) {
            uriImage = data!!.data
            imvBukti.setImageURI(uriImage)
        }
    }

//    private fun savePDF() {
//        val mDoc = com.itextpdf.text.Document()
//        val mFileName = SimpleDateFormat("yyyMMdd_HHmmss", Locale.getDefault())
//            .format(System.currentTimeMillis())
//
//        val mFilePath = Environment.getExternalStorageDirectory().toString() + "/" + mFileName +".pdf"
//
//        try {
//
//            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))
//            mDoc.open()
//
//            val data = transaksi.produk.toString().trim()
//            mDoc.addAuthor("KB CODER")
//            mDoc.add(Paragraph(data))
//            mDoc.close()
//            Toast.makeText(this, "$mFileName.pdf\n is create to \n$mFilePath", Toast.LENGTH_SHORT).show()
//
//        }catch (e: Exception){
//            Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int,
//                                            permissions: Array<out String>,
//                                            grantResults: IntArray
//    ) {
//        when(requestCode){
//            STORAGE_CADE -> {
//                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    savePDF()
//                }else{
//                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }

}