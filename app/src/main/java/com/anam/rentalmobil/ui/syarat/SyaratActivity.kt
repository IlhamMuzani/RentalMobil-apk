package com.anam.rentalmobil.ui.syarat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anam.rentalmobil.R
import kotlinx.android.synthetic.main.toolbar.*

class SyaratActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_syarat)

        activity()
    }

    fun activity()
    {
        tv_nama.text = "Syarat"
        ivKembali.setOnClickListener {
            onBackPressed()
        }
    }
}