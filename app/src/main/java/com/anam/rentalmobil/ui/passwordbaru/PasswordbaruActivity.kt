package com.anam.rentalmobil.ui.passwordbaru

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.anam.rentalmobil.R
import com.anam.rentalmobil.data.model.Constant
import com.anam.rentalmobil.data.model.user.ResponseUser
import com.anam.rentalmobil.ui.fragment.UserActivity
import com.anam.rentalmobil.ui.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_passwordbaru.*
import kotlinx.android.synthetic.main.activity_updateprofil.*
import kotlinx.android.synthetic.main.toolbar.*

class PasswordbaruActivity : AppCompatActivity(), PasswordbaruContract.View {

    lateinit var presenter: PasswordbaruPresenter

    private lateinit var sLoading: SweetAlertDialog
    private lateinit var sSuccess: SweetAlertDialog
    private lateinit var sError: SweetAlertDialog
    private lateinit var sAlert: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passwordbaru)
        presenter = PasswordbaruPresenter(this)
    }

    override fun initActivity() {

        sLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal !")
        sAlert = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Peringatan !")


        tv_nama.text ="Ubah Password"
    }

    override fun initListener() {

        ivKembali.setOnClickListener {
            onBackPressed()
        }

        btn_passworbaru.setOnClickListener {
            if (edit_ubahtextPassword.text!!.isEmpty()){
                showError("Masukkan Password")
            } else if (edit_ubahkonfirmasiPassword.text!!.isEmpty()){
              showError("Masukkan Konfirmasi Password")
            }
                presenter.passwordbaru(Constant.USER_ID, edit_ubahtextPassword.text.toString(), edit_ubahkonfirmasiPassword.text.toString())
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
            showSucces(responseUser.message)
        }else{
            showError(responseUser.message)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
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
}