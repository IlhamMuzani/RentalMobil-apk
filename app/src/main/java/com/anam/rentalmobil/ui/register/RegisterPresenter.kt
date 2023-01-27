package com.anam.rentalmobil.ui.register

import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.user.DataUser
import com.anam.rentalmobil.data.model.user.ResponseUser
import com.anam.rentalmobil.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterPresenter (val view: RegisterContract.View) : RegisterContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)
    }

    override fun insertregister(
        nik: String,
        nama: String,
        telp: String,
        gender: String,
        latitude: String,
        longitude: String,
        alamat: String,
        password: String,
        password_confirmation: String
    ) {
        view.onLoading(true, "Loading..")
        ApiService.endpoint.register(nik, nama, telp, gender, latitude, longitude, alamat, password, password_confirmation).enqueue(object: Callback<ResponseUser>{
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseUser: ResponseUser? = response.body()
                    view.onResult(responseUser!!)
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

}