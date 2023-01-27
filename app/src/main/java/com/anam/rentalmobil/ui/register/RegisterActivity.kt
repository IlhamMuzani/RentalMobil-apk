package com.anam.rentalmobil.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import com.anam.rentalmobil.R
import com.anam.rentalmobil.data.model.Constant
import com.anam.rentalmobil.data.model.user.ResponseUser
import com.anam.rentalmobil.network.ApiService
import com.anam.rentalmobil.ui.login.LoginActivity
import com.anam.rentalmobil.ui.maps.MapsActivity
import com.anam.rentalmobil.ui.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_updateprofil.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    lateinit var presenter: RegisterPresenter
    lateinit var telp: String
    private var gender: String? = null

    lateinit var sLoading: SweetAlertDialog
    lateinit var sError: SweetAlertDialog
    lateinit var sSuccess: SweetAlertDialog
    lateinit var sAlert: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        presenter = RegisterPresenter(this)

    }

    override fun onStart() {
        super.onStart()
        if (Constant.AREA != "") {
            tv_alamat1.text = Constant.AREA
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.LATITUDE = ""
        Constant.LONGITUDE = ""
    }

    override fun initActivity() {

        sLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal")
        sAlert = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Peringatan !")

        tv_nama.text = "Register"
        radiolakilaki.isChecked = true

    }

    override fun initListener() {

        ivKembali.setOnClickListener {
            onBackPressed()
        }

        Btn_Lokasi1.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        btn_register.setOnClickListener {
            val radioID = radio_JK.checkedRadioButtonId
            val radiobutton = findViewById<RadioButton>(radioID)
            gender = radiobutton.text.toString()

            if (edit_textNik.text!!.isEmpty()) {
                showError("Kolom NIK tidak boleh kosong !")
            } else if (edit_textName.text!!.isEmpty()) {
                showError("Kolom nama tidak boleh kosong !")
            } else if (Constant.LATITUDE == "") {
                showError("Pilih Lokasi !")
            } else if (gender == null) {
                showError("Pilih gender")
                radio_JK.requestFocus()
            } else if (edit_phone1.text!!.isEmpty()) {
                showError("Kolom telepon tidak boleh kosong !")
            } else if (edit_Password.text!!.isEmpty()) {
                showError("Kolom password tidak boleh kosong !")
            } else if (edit_Passwordconfirm.text!!.isEmpty()) {
                showError("Kolom password konfirmasi tidak boleh kosong !")
            } else
                presenter.insertregister(
                    edit_textNik.text.toString(),
                    edit_textName.text.toString(),
                    edit_phone1.text.toString(),
                    gender!!,
                    Constant.LATITUDE,
                    Constant.LONGITUDE,
                    tv_alamat1.text.toString(),
                    edit_Password.text.toString(),
                    edit_Passwordconfirm.text.toString()
                )
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

    override fun onResult(responseUser: ResponseUser) {
        if (responseUser.status) {
            showSuccesOk(responseUser.message)
        } else {
            showError(responseUser.message)
        }
    }

    override fun showSuccesOk(message: String) {
        sSuccess
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismissWithAnimation()
                finish()
                startActivity(Intent(this, LoginActivity::class.java))
            }.show()
    }

    override fun showSucces(message: String) {
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

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}