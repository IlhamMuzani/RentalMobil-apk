package com.anam.rentalmobil.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.anam.rentalmobil.R
import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.user.ResponseUser
import com.anam.rentalmobil.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*

class LoginActivity : AppCompatActivity(), LoginContract.View {

    lateinit var presenter: LoginPresenter
    lateinit var prefsManager: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter = LoginPresenter(this)
        prefsManager = PrefsManager(this)
    }

    override fun initActivity() {
        tv_nama.text ="Login"

    }

    override fun initListener() {

        ivKembali.setOnClickListener {
            onBackPressed()
        }

        btn_login.setOnClickListener {
            presenter.doLogin(edit_phone.text.toString(), edit_textPassword.text.toString())
        }

        text_viewRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun onLoading(loading: Boolean) {
        when (loading) {
            true -> {
                progress.visibility = View.VISIBLE
                btn_login.visibility = View.GONE
            }
            false -> {
                progress.visibility = View.GONE
                btn_login.visibility = View.VISIBLE
            }
        }
    }

    override fun onResult(responseUser: ResponseUser) {
        presenter.setPrefs(prefsManager, responseUser.user!!)
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