package com.anam.rentalmobil.ui.maps

import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.user.DataUser
import com.anam.rentalmobil.data.model.user.ResponseUser
import com.anam.rentalmobil.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsPresenter (val view: MapsContract.View) {

    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)
    }

}