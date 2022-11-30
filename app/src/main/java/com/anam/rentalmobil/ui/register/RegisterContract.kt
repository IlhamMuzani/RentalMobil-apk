package com.anam.rentalmobil.ui.register

import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.user.DataUser
import com.anam.rentalmobil.data.model.user.ResponseUser

interface RegisterContract {

    interface Presenter {
      fun insertregister(nik: String, nama: String, telp: String, alamat: String, gender: String, password: String, password_confirmation:String)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResult(responseUser: ResponseUser)
        fun showMessage(message: String)
    }
}