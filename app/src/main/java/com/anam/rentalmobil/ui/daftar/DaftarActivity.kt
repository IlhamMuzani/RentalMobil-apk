package com.anam.rentalmobil.ui.daftar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anam.rentalmobil.R
import com.anam.rentalmobil.ui.login.LoginActivity
import com.anam.rentalmobil.ui.petunjuk.PetunjukActivity
import com.anam.rentalmobil.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_daftar.*
import kotlinx.android.synthetic.main.toolbar.*

class DaftarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar)

        activity()
    }

    fun activity(){

        tv_nama.text = "Aplikasi Rental Mobil"

        ivKembali.setOnClickListener {
            onBackPressed()
        }

        layout_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        layout_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        layout_petunjuk.setOnClickListener {
            startActivity(Intent(this, PetunjukActivity::class.java))
        }
    }
}