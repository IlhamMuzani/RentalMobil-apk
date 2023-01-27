package com.anam.rentalmobil.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anam.rentalmobil.R
import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.user.DataUser
import com.anam.rentalmobil.data.model.user.ResponseUser
import com.anam.rentalmobil.ui.fragment.UserActivity
import com.anam.rentalmobil.ui.register.RegisterActivity
import com.anam.rentalmobil.ui.sweetalert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*

class LoginActivity : AppCompatActivity(), LoginContract.View {

    lateinit var presenter: LoginPresenter
    lateinit var prefsManager: PrefsManager

    private lateinit var sLoading: SweetAlertDialog
    private lateinit var sSuccess: SweetAlertDialog
    private lateinit var sError: SweetAlertDialog
    private lateinit var sAlert: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter = LoginPresenter(this)
        prefsManager = PrefsManager(this)
    }

    override fun initActivity() {
        tv_nama.text ="Login"

        sLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal !")
        sAlert = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Peringatan !")

    }

    override fun initListener() {

        ivKembali.setOnClickListener {
            onBackPressed()
        }

        btn_login.setOnClickListener {
            if (edit_phone.text!!.isEmpty()){
                showError("Masukkan Nomor Telepon !")
            }else if (edit_textPassword.text!!.isEmpty()){
                showError("Masukkan Password !")
            }else{
                presenter.doLogin(edit_phone.text.toString(), edit_textPassword.text.toString())
            }
        }

        text_viewRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        txv_dummy.setOnClickListener {
            dummy()
        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setConfirmText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResult(responseUser: ResponseUser) {
        val status: Boolean = responseUser.status
        val message: String = responseUser.message
        if (status){
            val user: DataUser = responseUser.user!!

            presenter.setPrefs(prefsManager, user)
            showSuccesOk(message)
        }else{
            if (status == false){
                showError(message)
            }
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

    fun dummy() {
        edit_phone.setText("pelanggan1")
        edit_textPassword.setText("pelanggan1")
    }
}