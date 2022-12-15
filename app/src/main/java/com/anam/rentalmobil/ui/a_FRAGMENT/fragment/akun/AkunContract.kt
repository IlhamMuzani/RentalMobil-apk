package com.ilham.taspesialisbangunan.ui.home.fragment.Akun

import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.user.ResponseUserdetail

interface AkunContract {

    interface Presenter {
        fun doLogin(prefsManager: PrefsManager)
        fun doLogout(prefsManager: PrefsManager)

        fun profildetail(id:String)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onResultLogin(prefsManager: PrefsManager)
        fun onResultLogout()
        fun onResult(responseUserdetail: ResponseUserdetail)
        fun showMessage(message: String)
    }

}