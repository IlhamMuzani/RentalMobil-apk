package com.anam.rentalmobil.ui.register

import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.user.DataUser
import com.anam.rentalmobil.data.model.user.ResponseUser

interface RegisterContract {

    interface Presenter {
      fun insertregister(nik: String, nama: String, telp: String, gender: String, latitude: String, longitude: String, alamat: String, password: String, password_confirmation:String)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String?= "Loading..")
        fun onResult(responseUser: ResponseUser)
        fun showSuccesOk(message: String)
        fun showSucces(message: String)
        fun showError(message: String)
        fun showAlert(message: String)
    }
}