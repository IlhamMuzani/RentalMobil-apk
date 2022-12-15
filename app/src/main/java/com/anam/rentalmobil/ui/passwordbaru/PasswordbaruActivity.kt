package com.anam.rentalmobil.ui.passwordbaru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.anam.rentalmobil.R
import com.anam.rentalmobil.data.model.Constant
import com.anam.rentalmobil.data.model.user.ResponseUser
import kotlinx.android.synthetic.main.activity_passwordbaru.*
import kotlinx.android.synthetic.main.activity_updateprofil.*
import kotlinx.android.synthetic.main.toolbar.*

class PasswordbaruActivity : AppCompatActivity(), PasswordbaruContract.View {

    lateinit var presenter: PasswordbaruPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passwordbaru)
        presenter = PasswordbaruPresenter(this)
    }

    override fun initActivity() {

        tv_nama.text ="Ubah Password"
    }

    override fun initListener() {

        ivKembali.setOnClickListener {
            onBackPressed()
        }

        btn_passworbaru.setOnClickListener {
            if (edit_ubahtextPassword.text!!.isEmpty()){
                edit_ubahtextPassword.error = "Masukkan Password"
                edit_ubahtextPassword.requestFocus()
            } else if (edit_ubahkonfirmasiPassword.text!!.isEmpty()){
                edit_ubahkonfirmasiPassword.error = "Masukkan Password Konfirmasi"
                edit_ubahkonfirmasiPassword.requestFocus()
            }
                presenter.passwordbaru(Constant.USER_ID, edit_ubahtextPassword.text.toString(), edit_ubahkonfirmasiPassword.text.toString())
        }
    }

    override fun onLoading(loading: Boolean) {
        when (loading) {
            true -> {
                progresspasswordbaru.visibility = View.VISIBLE
                btn_passworbaru.visibility = View.GONE
            }
            false -> {
                progresspasswordbaru.visibility = View.GONE
                btn_passworbaru.visibility = View.VISIBLE
            }
        }
    }

    override fun onResult(responseUser: ResponseUser) {
        showMessage(responseUser.message)
        finish()
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}