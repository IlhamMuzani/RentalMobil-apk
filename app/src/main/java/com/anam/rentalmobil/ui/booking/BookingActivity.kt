package com.anam.rentalmobil.ui.booking

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.anam.rentalmobil.R
import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.Booking.DataBooking
import com.anam.rentalmobil.data.model.Booking.ResponseBookingInsert
import com.anam.rentalmobil.data.model.Constant
import com.anam.rentalmobil.data.model.produk.DataProduk
import com.anam.rentalmobil.data.model.produk.ResponseProduk
import com.anam.rentalmobil.data.model.produk.ResponseProdukList
import com.anam.rentalmobil.data.model.user.ResponseSopirList
import com.anam.rentalmobil.ui.utils.FileUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lazday.poslaravel.util.GalleryHelper
import kotlinx.android.synthetic.main.activity_booking.*
import kotlinx.android.synthetic.main.activity_booking.view.*
import kotlinx.android.synthetic.main.dialog_bukti.view.*
import kotlinx.android.synthetic.main.dialog_konfirmasi.view.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.SimpleDateFormat
import java.util.*

class BookingActivity : AppCompatActivity(), BookingContract.View {

    lateinit var presenter: BookingPresenter
    lateinit var prefsManager: PrefsManager
    var harga: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        presenter = BookingPresenter(this)
        prefsManager = PrefsManager(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.getDetailproduk(Constant.PRODUK_ID)
    }

    override fun initActivity() {
        tv_nama.text = "Booking"
        tanggal()
//        spinnerlama()
    }

    override fun initListener() {

        ivKembali.setOnClickListener {
            onBackPressed()
        }

        btn_booking.setOnClickListener {
//            if (edt_lama.text!!.isEmpty()) {
//                edt_lama.error = " Kolom Tidak Boleh Kosong"
//                edt_lama.requestFocus()
//            } else {
            onShowdialogkonfirmasi()
        }

    }

    override fun onLoading(loading: Boolean) {
        when (loading) {
            true -> {
                progressbooking.visibility = View.VISIBLE
                btn_booking.visibility = View.GONE
            }
            false -> {
                progressbooking.visibility = View.GONE
                btn_booking.visibility = View.VISIBLE
            }
        }
    }

    override fun onResult(responseBookingInsert: ResponseBookingInsert) {
        showMessage(responseBookingInsert.message)
        finish()
    }

    override fun onShowdialogkonfirmasi() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_konfirmasi, null)

        view.btn_tolak.setOnClickListener {
            dialog.dismiss()
        }
        Constant.HARGA = (edt_lama.text.toString().toInt() * harga.toInt()).toString()
        view.txtHargakonfirmasi.text = (edt_lama.text.toString().toInt() * harga.toInt()).toString()
        view.btn_konfirmasi.setOnClickListener {

                presenter.inserBooking(
                    prefsManager.prefsId,
                    Constant.PRODUK_ID.toString(),
//                    Constant.KATEGORI_NAME,
                    edt_tanggal.text.toString(),
//                    Constant.LAMA_NAME,
                    edt_lama.text.toString(),
                   Constant.HARGA
                )
                dialog.dismiss()
                finish()
            }

            dialog.setContentView(view)
            dialog.show()
    }


    override fun onResultdetail(responseProduk: ResponseProduk) {
        harga = responseProduk.dataproduk!!.sewa.toInt()
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    fun tanggal() {
        val myCalender = Calendar.getInstance()
        val datePicter = DatePickerDialog.OnDateSetListener { view, year, month,
                                                              dayofmonth ->
            myCalender.set(Calendar.YEAR, year)
            myCalender.set(Calendar.MONTH, month)
            myCalender.set(Calendar.DAY_OF_MONTH, dayofmonth)
            updateLable(myCalender)
        }

        edt_tanggal.setOnClickListener {
            DatePickerDialog(
                this, datePicter, myCalender.get(Calendar.YEAR),
                myCalender.get(Calendar.MONTH),
                myCalender.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun updateLable(myCalendar: Calendar) {
        val myformat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myformat, Locale.UK)
        edt_tanggal.setText(sdf.format(myCalendar.time))
    }

//    fun spinnerlama() {
//
//        val arrayString = ArrayList<String>()
//        arrayString.add("- Pilih -")
//        arrayString.add("1 Hari")
//        arrayString.add("2 Hari")
//        arrayString.add("3 Hari")
//        arrayString.add("4 Hari")
//        arrayString.add("5 Hari")
//        arrayString.add("6 Hari")
//        arrayString.add("7 Hari")
//
//        val adapter = ArrayAdapter(this, R.layout.item_spinnerblack, arrayString.toTypedArray())
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        edt_lama.adapter = adapter
//        val selection = adapter.getPosition(Constant.LAMA_NAME)
//        edt_lama.setSelection(selection)
//        edt_lama.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                when (position) {
//                    0 -> {
//                        Constant.LAMA_ID = 0
//                        Constant.LAMA_NAME = "Pilih"
//                    }
//                    1 -> {
//                        Constant.LAMA_ID = 1
//                        Constant.LAMA_NAME = "1"
//
//                    }
//                    2 -> {
//                        Constant.LAMA_ID = 2
//                        Constant.LAMA_NAME = "2"
//                    }
//                    3 -> {
//                        Constant.LAMA_ID = 3
//                        Constant.LAMA_NAME = "3"
//                    }
//                    4 -> {
//                        Constant.LAMA_ID = 4
//                        Constant.LAMA_NAME = "4"
//                    }
//                    5 -> {
//                        Constant.LAMA_ID = 5
//                        Constant.LAMA_NAME = "5"
//                    }
//                    6 -> {
//                        Constant.LAMA_ID = 6
//                        Constant.LAMA_NAME = "6"
//                    }
//                    else -> {
//                        Constant.LAMA_ID = 7
//                        Constant.LAMA_NAME = "7"
//                    }
//                }
//                if (harga != 0 && Constant.LAMA_ID != 0) {
//                    val lama = Constant.LAMA_ID * harga
//                    edt_harga.setText((lama).toString())
//                }
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//
//            }
//        }
//
//    }
}