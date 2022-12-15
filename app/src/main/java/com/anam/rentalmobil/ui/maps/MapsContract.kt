package com.anam.rentalmobil.ui.maps

import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.user.DataUser
import com.anam.rentalmobil.data.model.user.ResponseUser

interface MapsContract {

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun showMessage(message: String)
    }
}