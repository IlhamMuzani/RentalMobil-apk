package com.anam.rentalmobil.ui.passwordbaru

import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.user.DataUser
import com.anam.rentalmobil.data.model.user.ResponseUser

interface PasswordbaruContract {

    interface Presenter {
        fun passwordbaru(id: Long, password: String, password_confirmation: String)
    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResult(responseUser: ResponseUser)
        fun showMessage(message: String)
    }
}