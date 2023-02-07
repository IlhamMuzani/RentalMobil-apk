package com.anam.rentalmobil.ui.checkUser

import com.anam.rentalmobil.data.model.user.ResponseUser
import com.anam.rentalmobil.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckUserPresenter(val view: CheckUserContract.View) : CheckUserContract.Presenter {

    init {
        view.initActivitity()
        view.initListener()
        view.onLoading(false)
    }

    override fun checkuser(nik: String, telp: String) {
        view.onLoading(true, "Loading..")
        ApiService.endpoint.check_user(nik, telp).enqueue(object : Callback<ResponseUser>{
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                view.onLoading(false)
                if (response.isSuccessful){
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