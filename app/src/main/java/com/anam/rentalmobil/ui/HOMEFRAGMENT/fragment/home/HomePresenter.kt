package com.ilham.taspesialisbangunan.ui.home.fragment.home

import com.anam.rentalmobil.data.model.produk.ResponseProdukList
import com.anam.rentalmobil.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePresenter (var view: HomeContract.View) : HomeContract.Presenter {


    override fun getProduk() {
        view.onLoading(true)
        ApiService.endpoint.getproduk().enqueue(object : Callback<ResponseProdukList> {
            override fun onResponse(
                call: Call<ResponseProdukList>,
                response: Response<ResponseProdukList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseProdukList: ResponseProdukList? = response.body()
                    view.onResult( responseProdukList!! )
                }
            }

            override fun onFailure(call: Call<ResponseProdukList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun Search(keyword: String) {
        view.onLoading(true)
        ApiService.endpoint.search(keyword).enqueue( object : Callback<ResponseProdukList>{
            override fun onResponse(
                call: Call<ResponseProdukList>,
                response: Response<ResponseProdukList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful){
                    val responseProdukList: ResponseProdukList? = response.body()
                    view.onResult(responseProdukList!!)
                }
            }

            override fun onFailure(call: Call<ResponseProdukList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }
}