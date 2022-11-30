package com.anam.rentalmobil.ui.HOMEFRAGMENT.fragment.detailproduk

import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.produk.ResponseProduk
import com.anam.rentalmobil.data.model.user.DataUser
import com.anam.rentalmobil.data.model.user.ResponseUser
import com.anam.rentalmobil.network.ApiService
import com.google.android.gms.common.api.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailprodukPresenter (val view: DetailprodukContract.View) : DetailprodukContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)
    }

    override fun getDetailproduk(id: Long) {
        view.onLoading(true)
        ApiService.endpoint.getDetailproduk(id).enqueue(object : Callback<ResponseProduk>{
            override fun onResponse(
                call: Call<ResponseProduk>,
                response: Response<ResponseProduk>
            ) {
                view.onLoading(false)
                if (response.isSuccessful){
                    val responseProduk:ResponseProduk? = response.body()
                    view.onResult(responseProduk!!)
                }
            }

            override fun onFailure(call: Call<ResponseProduk>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

}