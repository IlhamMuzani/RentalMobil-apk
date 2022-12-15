package com.anam.rentalmobil.ui.invoice

import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.transaksi.ResponseTransaksi
import com.anam.rentalmobil.data.model.user.DataUser
import com.anam.rentalmobil.data.model.user.ResponseUser
import com.anam.rentalmobil.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InvoicePresenter (val view: InvoiceContract.View) : InvoiceContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)
    }

    override fun getDetail(id: Long) {
        view.onLoading(true)
        ApiService.endpoint.detailtransaksi(id).enqueue(object : Callback<ResponseTransaksi>{
            override fun onResponse(
                call: Call<ResponseTransaksi>,
                response: Response<ResponseTransaksi>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseTransaksi: ResponseTransaksi? = response.body()
                    view.onResult(responseTransaksi!!)
                }
            }

            override fun onFailure(call: Call<ResponseTransaksi>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

}