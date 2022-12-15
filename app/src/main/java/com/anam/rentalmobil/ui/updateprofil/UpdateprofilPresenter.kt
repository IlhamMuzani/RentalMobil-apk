package com.anam.rentalmobil.ui.updateprofil

import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.user.DataUser
import com.anam.rentalmobil.data.model.user.ResponseUser
import com.anam.rentalmobil.data.model.user.ResponseUserdetail
import com.anam.rentalmobil.network.ApiService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UpdateprofilPresenter (val view: UpdateprofilContract.View) : UpdateprofilContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)
    }

    override fun getdetailprofil(id: String) {
        ApiService.endpoint.ProfilDetail(id).enqueue(object : Callback<ResponseUserdetail>{
            override fun onResponse(call: Call<ResponseUserdetail>, response: Response<ResponseUserdetail>) {
                if (response.isSuccessful){
                    val responseUserdetail: ResponseUserdetail? = response.body()
                    view.onResult(responseUserdetail!!)
                }
            }

            override fun onFailure(call: Call<ResponseUserdetail>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun Updateprofil(
        id: Long,
        nik: String,
        nama: String,
        telp: String,
        gender: String,
        latitude: String,
        longitude: String,
        alamat: String,
        foto: File?
    ) {
        val requestBody: RequestBody
        val multipartBody: MultipartBody.Part

        if (foto != null) {
            requestBody = RequestBody.create(MediaType.parse("image/*"), foto)
            multipartBody = MultipartBody.Part.createFormData("foto",
                foto.name, requestBody)
        } else {
            requestBody = RequestBody.create(MediaType.parse("image/*"), "")
            multipartBody= MultipartBody.Part.createFormData("foto",
                "", requestBody)
        }

        view.onLoading(true)
        ApiService.endpoint.Updateuser(id, nik, nama, telp, gender, latitude, longitude, alamat, multipartBody ) .enqueue(object : Callback<ResponseUser> {
            override fun onResponse(
                call: Call<ResponseUser>,
                response: Response<ResponseUser>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responsePelangganUpdate: ResponseUser? = response.body()
                    view.onResultupdate( responsePelangganUpdate!! )
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun setPrefs(prefsManager: PrefsManager, dataUser: DataUser) {
        prefsManager.prefIsLogin = true
        prefsManager.prefsId = dataUser.id!!
        prefsManager.prefs_is_nik = dataUser.nik!!
        prefsManager.prefs_is_nama = dataUser.nama!!
    }

}