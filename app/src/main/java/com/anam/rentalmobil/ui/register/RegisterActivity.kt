package com.anam.rentalmobil.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.anam.rentalmobil.R
import com.anam.rentalmobil.data.model.Constant
import com.anam.rentalmobil.data.model.user.ResponseUser
import com.anam.rentalmobil.network.ApiService
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    lateinit var presenter: RegisterPresenter
    lateinit var telp: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        presenter = RegisterPresenter(this)

    }


    override fun initActivity() {

        tv_nama.text ="Register"

    }

    override fun initListener() {

        ivKembali.setOnClickListener {
            onBackPressed()
        }

        btn_register.setOnClickListener {
            if (edit_textNik.text!!.isEmpty()){
                edit_textNik.error = "Kolom Tidak Boleh Kosong"
                edit_textNik.requestFocus()
            } else if (edit_textName.text!!.isEmpty()){
                edit_textName.error = "Kolom Tidak Boleh Kosong"
                edit_textName.requestFocus()
            } else if (edit_phone1.text!!.isEmpty()){
                edit_phone1.error = "Kolom Tidak Boleh Kosong"
                edit_phone1.requestFocus()
            } else if (edit_textAlamat.text!!.isEmpty()){
                edit_textAlamat.error = "Kolom Tidak Boleh Kosong"
                edit_textAlamat.requestFocus()
            } else if (edit_Password.text!!.isEmpty()){
                edit_Password.error = "Kolom Tidak Boleh Kosong"
                edit_Password.requestFocus()
            } else if (edit_Passwordconfirm.text!!.isEmpty()){
                edit_Passwordconfirm.error = "Kolom Tidak Boleh Kosong"
                edit_Passwordconfirm.requestFocus()
            } else
                presenter.insertregister(edit_textNik.text.toString(), edit_textName.text.toString(), edit_phone1.text.toString(),edit_textAlamat.text.toString(), radio_JK.checkedRadioButtonId.toString(), edit_Password.text.toString(), edit_Passwordconfirm.text.toString())
        }
    }

    override fun onLoading(loading: Boolean) {
        when (loading) {
            true -> {
                progressregis.visibility = View.VISIBLE
                btn_register.visibility = View.GONE
            }
            false -> {
                progressregis.visibility = View.GONE
                btn_register.visibility = View.VISIBLE
            }
        }
    }

    override fun onResult(responseUser: ResponseUser) {
        if (responseUser.status) {
            responseUser.user
            showMessage(responseUser.message)
            finish()
        }else{
            showMessage(responseUser.message)
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}