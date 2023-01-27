package com.anam.rentalmobil.ui.fragment.fragment.notifications.detailtransaksi

import com.anam.rentalmobil.data.model.rekening.ResponseRekening
import com.anam.rentalmobil.data.model.transaksi.ResponseTransaksi
import com.anam.rentalmobil.data.model.transaksi.ResponseTransaksiUpdate
import com.anam.rentalmobil.network.ApiService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class DetailtransaksiPresenter (val view: DetailtransaksiContract.View) : DetailtransaksiContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)
    }

    override fun getDetailtransaksi(id: Long) {
        view.onLoading(true, "Loading..")
        ApiService.endpoint.detailtransaksi(id).enqueue(object : Callback<ResponseTransaksi>{
            override fun onResponse(
                call: Call<ResponseTransaksi>,
                response: Response<ResponseTransaksi>
            ) {
                view.onLoading(false)
                if (response.isSuccessful){
                    val responseTransaksi:ResponseTransaksi? = response.body()
                    view.onResult(responseTransaksi!!)
                }
            }

            override fun onFailure(call: Call<ResponseTransaksi>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun buktipembayaran(id: Long, bukti: File) {
        val requestBody: RequestBody = RequestBody.create(MediaType.parse("image/*"), bukti)
        val multipartBody: MultipartBody.Part = MultipartBody.Part.createFormData("bukti",
        bukti.name, requestBody)
//        view.onLoading(true)
        ApiService.endpoint.buktiPembayaran(id, multipartBody).enqueue(object :Callback<ResponseTransaksiUpdate>{
            override fun onResponse(
                call: Call<ResponseTransaksiUpdate>,
                response: Response<ResponseTransaksiUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful){
                    val responseTransaksiUpdate: ResponseTransaksiUpdate? = response.body()
                    view.onResultUpdate(responseTransaksiUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponseTransaksiUpdate>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun gettampilrekening() {
        view.onLoading(true, "Loading..")
        ApiService.endpoint.rekening().enqueue(object : Callback<ResponseRekening>{
            override fun onResponse(
                call: Call<ResponseRekening>,
                response: Response<ResponseRekening>
            ) {
                view.onLoading(false)
                if (response.isSuccessful){
                    val responseRekening : ResponseRekening? = response.body()
                    view.onResultTampilrekening(responseRekening!!)
                }
            }

            override fun onFailure(call: Call<ResponseRekening>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

}