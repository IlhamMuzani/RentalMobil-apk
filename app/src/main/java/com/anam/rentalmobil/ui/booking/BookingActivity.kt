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
import kotlinx.android.synthetic.main.activity_booking.*
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
        btn_booking.visibility = View.GONE
        presenter.getSopir()
        presenter.getDetailproduk(Constant.PRODUK_ID)
    }

    override fun initActivity() {
        tv_nama.text ="Booking"
//        tvDatePicker = findViewById(R.id.edt_tanggal)

        tanggal()
        spinnerarea()
        spinnerkategori()
        spinnerlama()
    }
    override fun initListener() {

        ivKembali.setOnClickListener {
            onBackPressed()
        }

        btn_booking.setOnClickListener {
            if (Constant.KATEGORI_ID == 0) {
                showMessage("Pilih Kategori Terlebih Dahulu")
//           tanggal belum ada show messege
            } else if (Constant.LAMA_ID == 0) {
                showMessage("Pilih Lama Peminjaman")
            } else if (edt_harga.text!!.isEmpty()) {
                edt_harga.error = " Kolom Tidak Boleh Kosong"
                edt_harga.requestFocus()
            } else {

                presenter.inserBooking(
                    prefsManager.prefsId,
                    Constant.PRODUK_ID.toString(),
                    Constant.KATEGORI_NAME,
                    edt_tanggal.text.toString(),
                    Constant.LAMA_NAME,
                    edt_harga.text.toString()
                )
            }
        }

        btn_bookingtrevel.setOnClickListener {

            if (Constant.KATEGORI_ID == 0) {
                showMessage("Pilih Kategori Terlebih Dahulu")
                //tanggal belum ana showmess
            } else if (Constant.KATEGORI_ID == 0) {
               showMessage("Pilih Sopir")
            } else if (Constant.AREA_ID == 0) {
                showMessage("Pilih Area")
            } else if (Constant.LAMA_ID == 0) {
                showMessage("Pilih Lama Peminjaman")
                edt_lama.requestFocus()
            } else if (edt_harga.text!!.isEmpty()) {
                edt_harga.error = " Kolom Tidak Boleh Kosong"
                edt_harga.requestFocus()
            } else {

                presenter.inserBookingtrevel(
                    prefsManager.prefsId,
                    Constant.PRODUK_ID.toString(),
                    Constant.KATEGORI_NAME,
                    Constant.SOPIR_ID.toString(),
                    Constant.AREA_NAME,
                    edt_tanggal.text.toString(),
                    Constant.LAMA_NAME,
                    edt_harga.text.toString()
                )
            }

        }
    }

    override fun onLoading(loading: Boolean) {
        when (loading) {
            true -> {
                progressbooking.visibility = View.VISIBLE
                btn_booking.visibility = View.GONE
                btn_bookingtrevel.visibility = View.GONE
            }
            false -> {
                progressbooking.visibility = View.GONE
                btn_booking.visibility = View.VISIBLE
                btn_bookingtrevel.visibility = View.VISIBLE
            }
        }
    }

    override fun onResult(responseBookingInsert: ResponseBookingInsert) {
        showMessage(responseBookingInsert.message)
        finish()
    }

    override fun onResultrevel(responseBookingInsert: ResponseBookingInsert) {
        showMessage(responseBookingInsert.message)
        finish()
    }

    override fun onResultSopir(responseSopirList: ResponseSopirList) {
        spinnersopir(responseSopirList)
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

    fun tanggal(){
        val myCalender = Calendar.getInstance()
        val datePicter = DatePickerDialog.OnDateSetListener { view, year, month,
                                                              dayofmonth ->
            myCalender.set(Calendar.YEAR,year)
            myCalender.set(Calendar.MONTH,month)
            myCalender.set(Calendar.DAY_OF_MONTH,dayofmonth)
            updateLable(myCalender)
        }

        edt_tanggal.setOnClickListener {
            DatePickerDialog(this, datePicter, myCalender.get(Calendar.YEAR),
                myCalender.get(Calendar.MONTH),
                myCalender.get(Calendar.DAY_OF_MONTH)).show()
        }
    }
    private fun updateLable(myCalendar: Calendar) {
        val myformat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myformat, Locale.UK)
        edt_tanggal.setText(sdf.format(myCalendar.time))
    }

    fun spinnerkategori() {

        val arrayString = ArrayList<String>()
        arrayString.add("- Pilih Kategori -")
        arrayString.add("Sewa Rental")
        arrayString.add("Sewa Trevel")

        val adapter = ArrayAdapter(this, R.layout.item_spinnerblack, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edt_kategori.adapter = adapter
        val selection = adapter.getPosition(Constant.KATEGORI_NAME)
        edt_kategori.setSelection(selection)
        edt_kategori.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        Constant.KATEGORI_ID = 0
                        Constant.KATEGORI_NAME = "Pilih Kategori"
                        btn_booking.visibility = View.GONE
                        btn_booking.visibility = View.VISIBLE
                    }
                    1 -> {
                        Constant.KATEGORI_ID = 1
                        Constant.KATEGORI_NAME = "Rental"
                        layoutsopirdanarea.visibility = View.GONE
                        btn_bookingtrevel.visibility = View.GONE
                        btn_booking.visibility = View.VISIBLE
                    }
                    2 -> {
                        Constant.KATEGORI_ID = 2
                        Constant.KATEGORI_NAME = "Travel"
                        layoutsopirdanarea.visibility = View.VISIBLE
                        btn_bookingtrevel.visibility = View.VISIBLE
                        btn_booking.visibility = View.GONE
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

    fun spinnersopir(responseSopirList: ResponseSopirList) {

        val arrayString = ArrayList<String>()
        arrayString.add("- Pilih Sopir -")
        for (sopir in responseSopirList.datasopir){arrayString.add(sopir.nama!!)}

        val adapter = ArrayAdapter(this, R.layout.item_spinnerblack, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edt_sopir.adapter = adapter
        val selection = adapter.getPosition(Constant.SOPIR_NAME)
        edt_sopir.setSelection(selection)
        edt_sopir.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        Constant.SOPIR_ID = 0
                        Constant.SOPIR_NAME = "Pilih Sopir"
                        btn_booking.visibility = View.GONE
                    }
                    else -> {
                        presenter.getSopir()
                        val namaSopir = responseSopirList.datasopir[position - 1].nama
                        Constant.SOPIR_ID = position
                        Constant.SOPIR_NAME = namaSopir.toString()
                        btn_booking.visibility = View.GONE
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

    fun spinnerarea() {

        val arrayString = ArrayList<String>()
        arrayString.add("- Pilih Area -")
        arrayString.add("Dalam Kota")
        arrayString.add("Luar Kota")

        val adapter = ArrayAdapter(this, R.layout.item_spinnerblack, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edt_area.adapter = adapter
        val selection = adapter.getPosition(Constant.AREA_NAME)
        edt_area.setSelection(selection)
        edt_area.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        Constant.AREA_ID = 0
                        Constant.AREA_NAME = "Pilih Area"
                    }
                    1 -> {
                        Constant.AREA_ID = 1
                        Constant.AREA_NAME = "dalam"
                    }
                    2 -> {
                        Constant.AREA_ID = 2
                        Constant.AREA_NAME = "luar"
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

    fun spinnerlama() {

        val arrayString = ArrayList<String>()
        arrayString.add("- Pilih -")
        arrayString.add("1 Hari")
        arrayString.add("2 Hari")
        arrayString.add("3 Hari")
        arrayString.add("4 Hari")
        arrayString.add("5 Hari")
        arrayString.add("6 Hari")
        arrayString.add("7 Hari")

        val adapter = ArrayAdapter(this, R.layout.item_spinnerblack, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edt_lama.adapter = adapter
        val selection = adapter.getPosition(Constant.LAMA_NAME)
        edt_lama.setSelection(selection)
        edt_lama.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        Constant.LAMA_ID = 0
                        Constant.LAMA_NAME = "Pilih"
                    }
                    1 -> {
                        Constant.LAMA_ID = 1
                        Constant.LAMA_NAME = "1 Hari"

                    }
                    2 -> {
                        Constant.LAMA_ID = 2
                        Constant.LAMA_NAME = "2 Hari"
                    }
                    3 -> {
                        Constant.LAMA_ID = 3
                        Constant.LAMA_NAME = "3 Hari"
                    }
                    4 -> {
                        Constant.LAMA_ID = 4
                        Constant.LAMA_NAME = "4 Hari"
                    }
                    5 -> {
                        Constant.LAMA_ID = 5
                        Constant.LAMA_NAME = "5 Hari"
                    }
                    6 -> {
                        Constant.LAMA_ID = 6
                        Constant.LAMA_NAME = "6 Hari"
                    }
                    else -> {
                        Constant.LAMA_ID = 7
                        Constant.LAMA_NAME = "7 Hari"
                    }
                }
                if(harga != 0 && Constant.LAMA_ID != 0) {
                    val lama = Constant.LAMA_ID * harga
                    edt_harga.setText((lama).toString())
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

}