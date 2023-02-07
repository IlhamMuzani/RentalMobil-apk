package com.anam.rentalmobil.ui.checkUser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anam.rentalmobil.R
import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.Constant
import com.anam.rentalmobil.data.model.user.ResponseUser
import com.anam.rentalmobil.ui.passwordbaru.PasswordbaruActivity
import com.anam.rentalmobil.ui.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_konfirmasiphone.*
import kotlinx.android.synthetic.main.toolbar.*

class CheckUserActivity : AppCompatActivity(), CheckUserContract.View {

    lateinit var presenter: CheckUserPresenter
    lateinit var telp : String
    lateinit var prefsManager: PrefsManager

    private lateinit var sLoading: SweetAlertDialog
    private lateinit var sSuccess: SweetAlertDialog
    private lateinit var sError: SweetAlertDialog
    private lateinit var sAlert: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konfirmasiphone)
        presenter = CheckUserPresenter(this)
        prefsManager = PrefsManager(this)
    }

    override fun initActivitity() {

    }

    override fun initListener() {

        sLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal !")
        sAlert = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Peringatan !")

        ivKembali.setOnClickListener {
            onBackPressed()
        }

        txvdatadummy.setOnClickListener {
            dataDummy()
        }

        btn_konfirmasi2.setOnClickListener {
            if (edt_nik2.text!!.isEmpty()){
                showError("Masukkan Nik")
            } else if (edit_phone22.text!!.isEmpty()){
                showError("Masukan Nomor Telepon")
            }else
                presenter.checkuser(edt_nik2.text.toString(), edit_phone22.text.toString())
        }

    }

    override fun onLoading(loading: Boolean, message: String?) {
        when(loading){
            true-> sLoading.setContentText(message).show()
            false->sLoading.dismiss()
        }
    }

    override fun onResult(responseUser: ResponseUser) {
        if (responseUser.status) {
            Constant.USER_ID = responseUser.user!!.id!!.toLong()
            showSuccesOk(responseUser.message)
        }else{
            showError(responseUser.message)
        }
    }

    fun dataDummy(){
        edt_nik2.setText("3660851872903294")
        edit_phone22.setText("pelanggan1")
    }

    override fun showSuccesOk(message: String) {
        sSuccess
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismissWithAnimation()
                finish()
                startActivity(Intent(this, PasswordbaruActivity::class.java))
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
}