package com.anam.rentalmobil.ui.fragment.fragment.home

import com.anam.rentalmobil.data.model.produk.ResponseProdukList
import com.anam.rentalmobil.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePresenter (var view: HomeContract.View) : HomeContract.Presenter {


    override fun getProduk() {
        view.onLoadingswet(true, "Loading..")
        view.onLoading(true)
        ApiService.endpoint.getproduk().enqueue(object : Callback<ResponseProdukList> {
            override fun onResponse(
                call: Call<ResponseProdukList>,
                response: Response<ResponseProdukList>
            ) {
                view.onLoadingswet(false)
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseProdukList: ResponseProdukList? = response.body()
                    view.onResult( responseProdukList!! )
                }
            }

            override fun onFailure(call: Call<ResponseProdukList>, t: Throwable) {
                view.onLoadingswet(false)
                view.onLoading(false)
            }

        })
    }

    override fun Searchproduk(keyword: String, kategori: String) {
        view.onLoading(true)
        view.onLoadingswet(true, "Loading..")
        ApiService.endpoint.Searchproduk(keyword, kategori).enqueue( object : Callback<ResponseProdukList>{
            override fun onResponse(
                call: Call<ResponseProdukList>,
                response: Response<ResponseProdukList>
            ) {
                view.onLoading(false)
                view.onLoadingswet(false, "Loading..")
                if (response.isSuccessful){
                    val responseProdukList: ResponseProdukList? = response.body()
                    view.onResult(responseProdukList!!)
                }
            }

            override fun onFailure(call: Call<ResponseProdukList>, t: Throwable) {
                view.onLoading(false)
                view.onLoadingswet(false, "Loading..")
            }

        })
    }
}