package com.anam.rentalmobil.ui.updateprofil

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.anam.rentalmobil.R
import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.Constant
import com.anam.rentalmobil.data.model.user.DataUser
import com.anam.rentalmobil.data.model.user.ResponseUser
import com.anam.rentalmobil.data.model.user.ResponseUserdetail
import com.anam.rentalmobil.ui.login.LoginActivity
import com.anam.rentalmobil.ui.maps.MapsActivity
import com.anam.rentalmobil.ui.sweetalert.SweetAlertDialog
import com.anam.rentalmobil.ui.utils.FileUtils
import com.anam.rentalmobil.ui.utils.GlideHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.lazday.poslaravel.util.GalleryHelper
import kotlinx.android.synthetic.main.activity_updateprofil.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.concurrent.TimeUnit

class UpdateprofilActivity : AppCompatActivity(), UpdateprofilContract.View {

    private var uriImage: Uri? = null
    private var pickImage = 1
    lateinit var presenter: UpdateprofilPresenter
    lateinit var prefsManager: PrefsManager
    lateinit var telp: String
    lateinit var user: DataUser
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    lateinit var sLoading: SweetAlertDialog
    lateinit var sError: SweetAlertDialog
    lateinit var sSuccess: SweetAlertDialog
    lateinit var sAlert: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_updateprofil)
        presenter = UpdateprofilPresenter(this)
        prefsManager = PrefsManager(this)
        presenter.getdetailprofil(prefsManager.prefsId)

    }

    override fun onStart() {
        super.onStart()
            if (Constant.AREA != "") { tv_alamat.text = Constant.AREA }
    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.LATITUDE = ""
        Constant.LONGITUDE = ""
    }

    override fun initActivity() {

        tv_nama.text ="Register"

        sLoading = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal")
        sAlert = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Peringatan !")

    }

    override fun initListener() {

        ivKembali.setOnClickListener {
            onBackPressed()
        }

        Btn_Lokasi.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        fotoprofile.setOnClickListener {
            if (GalleryHelper.permissionGallery(this, this, pickImage)) {
                GalleryHelper.openGallery(this)
            }
        }

        btn_ubahprofil.setOnClickListener {
            val radioID = radio_ubahJK.checkedRadioButtonId
            val radiobutton = findViewById<RadioButton>(radioID)
            val gender = radiobutton.text

            if (edit_ubahtextNik.text!!.isEmpty()){
                edit_ubahtextNik.error = "Masukan NIK"
                edit_ubahtextNik.requestFocus()
            }else if (edit_ubahtextName.text!!.isEmpty()){
                edit_ubahtextName.error = "Masukan Nama"
                edit_ubahtextName.requestFocus()
            }else if (edit_ubahphone.text!!.isEmpty()){
                edit_ubahtextName.error = "Masukan Phone"
                edit_ubahtextName.requestFocus()
            } else {
                presenter.Updateprofil(Constant.USER_ID, edit_ubahtextNik.text.toString(), edit_ubahtextName.text.toString(), edit_ubahphone.text.toString(), gender.toString(), Constant.LATITUDE, Constant.LONGITUDE, tv_alamat.text.toString(), FileUtils.getFile(this, uriImage))
            }
        }
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> {
                sLoading.setContentText(message).show()
            }
            false -> {
                sLoading.dismiss()
            }
        }
    }

    override fun onResult(responseUserdetail: ResponseUserdetail) {
        user = responseUserdetail.user

        if (user.foto.isNullOrEmpty()){
        }else{
            GlideHelper.setImage(this, Constant.IP_IMAGE + user.foto, fotoprofile)
        }

        Constant.LATITUDE = user.latitude!!
        Constant.LONGITUDE = user.longitude!!
        edit_ubahtextNik.setText( user.nik )
        edit_ubahtextName.setText( user.nama )
        edit_ubahphone.setText( user.telp )
        tv_alamat.setText( user.alamat )

        if (user.gender == "P"){
            radioP.isChecked = true
        }else{
            radioL.isChecked = true
        }

    }

    override fun onResultupdate(responseUser: ResponseUser) {
        val status: Boolean = responseUser.status
        val msg: String = responseUser.message!!

        if (status){
            if (msg == "Lakukan verifikasi OTP untuk memperbarui nomor telepon"){
                val user = responseUser.user!!
                Constant.UPDATE = true
                Constant.USER_ID = user.id!!.toLong()
                startPhoneNumberVerification("+62$telp")
            }else {
                showSuccesOk(responseUser.message)
            }
        }else{
            showError(responseUser.message)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImage && resultCode == Activity.RESULT_OK) {
            uriImage = data!!.data
            fotoprofile.setImageURI(uriImage)
        }
    }

    fun startPhoneNumberVerification(phone: String) {
//        sLoading.setTitleText("Loading...").show()
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun showSuccesOk(message: String) {
        sSuccess
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismissWithAnimation()
                finish()
            }.show()
    }

    override fun showSucces(message: String) {
        sSuccess
            .setContentText(message)
            .setTitleText("Ok")
            .setConfirmClickListener {
                it.dismissWithAnimation()
            }.show()
    }

    override fun showError(message: String) {
        sError
            .setContentText(message)
            .setConfirmText("Gagal")
            .setConfirmClickListener {
                it.dismiss()
            }.show()
    }

    override fun showAlert(message: String) {
        sAlert
            .setContentText(message)
            .setTitleText("Ya")
            .setConfirmClickListener {
                it.dismissWithAnimation()
            }
            .setTitleText("Nanti")
            .setConfirmClickListener {
                it.dismiss()
            }.show()
        sAlert.setCancelable(true)
    }


}