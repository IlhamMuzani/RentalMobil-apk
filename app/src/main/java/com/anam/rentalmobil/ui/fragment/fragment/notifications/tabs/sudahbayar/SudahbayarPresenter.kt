package com.anam.rentalmobil.ui.fragment.fragment.notifications.tabs.sudahbayar

import com.anam.rentalmobil.data.model.transaksi.ResponseTransaksiList
import com.anam.rentalmobil.data.model.transaksi.ResponseTransaksiUpdate
import com.anam.rentalmobil.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SudahbayarPresenter (var view: SudahbayarContract.View) : SudahbayarContract.Presenter {


    override fun getStatussudahbayar(kd_user: Long) {
        view.onloading(true)
        view.onloadingswet(true, "Loading..")
        ApiService.endpoint.getTransaksisudahbayar(kd_user).enqueue(object : Callback<ResponseTransaksiList>{
            override fun onResponse(
                call: Call<ResponseTransaksiList>,
                response: Response<ResponseTransaksiList>
            ) {
                view.onloading(false)
                view.onloadingswet(false, "Loading..")
                if (response.isSuccessful){
                    val responseTransaksiList: ResponseTransaksiList? = response.body()
                    view.onResult(responseTransaksiList!!)
                }
            }

            override fun onFailure(call: Call<ResponseTransaksiList>, t: Throwable) {
                view.onloading(false)
                view.onloadingswet(false, "Loading..")
            }

        })
    }

    override fun deletetransaksi(id: Long) {
        view.onloading(true)
        view.onloadingswet(true, "Loading..")
        ApiService.endpoint.deletetransaksi(id).enqueue(object : Callback<ResponseTransaksiUpdate>{
            override fun onResponse(
                call: Call<ResponseTransaksiUpdate>,
                response: Response<ResponseTransaksiUpdate>
            ) {
                view.onloading(false)
                view.onloadingswet(false, "Loading..")
                if (response.isSuccessful) {
                    val responseTransaksiUpdate: ResponseTransaksiUpdate? = response.body()
                    view.onResultDelete( responseTransaksiUpdate!! )
                }
            }

            override fun onFailure(call: Call<ResponseTransaksiUpdate>, t: Throwable) {
                view.onloading(false)
                view.onloadingswet(false, "Loading..")
            }

        })
    }

}