package com.anam.rentalmobil.ui.updateprofil

import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.user.DataUser
import com.anam.rentalmobil.data.model.user.ResponseUser
import com.anam.rentalmobil.data.model.user.ResponseUserdetail
import java.io.File

interface UpdateprofilContract {

    interface Presenter {
        fun getdetailprofil(id: String)
      fun Updateprofil(id: Long, nik: String, nama: String, telp: String, gender: String, latitude: String, Longitude: String, alamat: String, foto: File?)

      fun setPrefs(prefsManager: PrefsManager, dataUser: DataUser)

    }

    interface View {
        fun initActivity()
        fun initListener()
        fun onLoading(loading: Boolean)
        fun onResult(responseUserdetail: ResponseUserdetail)
        fun onResultupdate(responseUser: ResponseUser)
        fun showMessage(message: String)
    }
}