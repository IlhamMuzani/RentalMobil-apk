package com.anam.rentalmobil.ui.login

import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.user.DataUser
import com.anam.rentalmobil.data.model.user.ResponseUser

interface LoginContract {

    interface Presenter {
        fun doLogin(telp:String, password:String)
        fun setPrefs(prefsManager: PrefsManager, dataUser: DataUser)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResult(responseUser: ResponseUser)
        fun showSuccesOk(message: String)
        fun showSucces(message: String)
        fun showError(message: String)
        fun showAlert(message: String)
    }
}