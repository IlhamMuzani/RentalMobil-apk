package com.anam.rentalmobil.ui.checkUser

import com.anam.rentalmobil.data.model.user.ResponseUser

interface CheckUserContract {

    interface Presenter {
        fun checkuser(nik: String, telp: String)
    }

    interface View {
        fun initActivitity()
        fun initListener()
        fun onLoading(loading: Boolean, message:String? = "Loading..")
        fun onResult(responseUser: ResponseUser)
        fun showSuccesOk(message: String)
        fun showSucces(message: String)
        fun showError(message: String)
        fun showAlert(message: String)
    }
}