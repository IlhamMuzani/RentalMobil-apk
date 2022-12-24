package com.anam.rentalmobil.ui.petunjuk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anam.rentalmobil.R
import kotlinx.android.synthetic.main.toolbar.*

class PetunjukActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_petunjuk)
        fungsi()
    }

    fun fungsi(){
        tv_nama.text ="Petunjuk"

        ivKembali.setOnClickListener {
            onBackPressed()
        }
    }
}