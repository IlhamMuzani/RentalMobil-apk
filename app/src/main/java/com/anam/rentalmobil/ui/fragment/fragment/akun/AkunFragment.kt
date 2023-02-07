package com.anam.rentalmobil.ui.fragment.fragment.akun

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.anam.rentalmobil.R
import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.Constant
import com.anam.rentalmobil.data.model.user.ResponseUserdetail
import com.anam.rentalmobil.ui.fragment.UserActivity
import com.anam.rentalmobil.ui.passwordbaru.PasswordbaruActivity
import com.anam.rentalmobil.ui.sweetalert.SweetAlertDialog
import com.anam.rentalmobil.ui.syarat.SyaratActivity
import com.anam.rentalmobil.ui.updateprofil.UpdateprofilActivity
import com.anam.rentalmobil.ui.utils.GlideHelper
import com.anam.rentalmobil.ui.utils.MapsHelper

class AkunFragment : Fragment(), AkunContract.View {

    lateinit var prefsManager: PrefsManager
    lateinit var presenter: AkunPresenter

    lateinit var BtnUbahProfil: RelativeLayout
    lateinit var Btnsyarat: RelativeLayout
    lateinit var Btnpasswordbaru: RelativeLayout
    lateinit var txvNama: TextView
    lateinit var imvGambar: ImageView
    lateinit var txvNIK: TextView
    lateinit var TxvAlamat: TextView
    lateinit var Txvphone: TextView
    lateinit var TxvGenderlakilaki: TextView
    lateinit var TxvGenderperempuan: TextView
    lateinit var TxvLogout: TextView

    lateinit var sLoading: SweetAlertDialog
    lateinit var sAlert: SweetAlertDialog
    lateinit var sError: SweetAlertDialog
    lateinit var sSuccess: SweetAlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_akun, container, false)

        initFragment(view)

        prefsManager = PrefsManager(requireActivity())
        presenter = AkunPresenter(this)
        presenter.doLogin(prefsManager)


        return view
    }

    override fun initFragment(view: View) {
        sLoading = SweetAlertDialog(requireActivity(), SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(
            requireActivity(),
            SweetAlertDialog.SUCCESS_TYPE
        ).setTitleText("Berhasil")
        sError =
            SweetAlertDialog(requireActivity(), SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal")
        sAlert = SweetAlertDialog(
            requireActivity(),
            SweetAlertDialog.WARNING_TYPE
        ).setTitleText("Perhatian!")

        MapsHelper.permissionMap(requireContext(), requireActivity())
        BtnUbahProfil = view.findViewById(R.id.btn_ubahProfil)
        Btnsyarat = view.findViewById(R.id.btn_syarat)
        Btnpasswordbaru = view.findViewById(R.id.btn_ubahPassword)
        txvNama = view.findViewById(R.id.txv_nama)
        imvGambar = view.findViewById(R.id.imvgambar)
        txvNIK = view.findViewById(R.id.txvNIK)
//        TxvAlamat = view.findViewById(R.id.txvAlamat)
        Txvphone = view.findViewById(R.id.txvPhone)
        TxvGenderlakilaki = view.findViewById(R.id.txvGenderlakilaki)
        TxvGenderperempuan = view.findViewById(R.id.txvGenderperempuan)
        TxvLogout = view.findViewById(R.id.txvLogout)


        BtnUbahProfil.setOnClickListener {
            Constant.USER_ID = prefsManager.prefsId.toLong()
            startActivity(Intent(requireActivity(), UpdateprofilActivity::class.java))
        }

        Btnsyarat.setOnClickListener {
            startActivity(Intent(requireActivity(), SyaratActivity::class.java))
        }

        Btnpasswordbaru.setOnClickListener {
            Constant.USER_ID = prefsManager.prefsId.toLong()
            startActivity(Intent(requireActivity(), PasswordbaruActivity::class.java))
        }

        TxvLogout.setOnClickListener {
            presenter.doLogout(prefsManager)
        }

    }

    override fun onStart() {
        super.onStart()
        presenter.profildetail(prefsManager.prefsId)

    }

    override fun onResultLogin(prefsManageruser: PrefsManager) {

    }

    override fun onResultLogout() {
        showSuccessOk("Berhasil Logout")
    }

    override fun onResult(responseUserdetail: ResponseUserdetail) {
        val akun = responseUserdetail.user
        txvNama.setText(akun!!.nama)
        txvNIK.setText(akun!!.nik)
        Txvphone.setText(akun!!.telp)
        GlideHelper.setImage(requireActivity(), Constant.IP_IMAGE + akun!!.foto, imvGambar)
        if (responseUserdetail.user.gender == "P") {
            TxvGenderperempuan.visibility = View.VISIBLE
            TxvGenderlakilaki.visibility = View.GONE
        } else {
            TxvGenderlakilaki.visibility = View.VISIBLE
            TxvGenderperempuan.visibility = View.GONE
        }
    }

    override fun showSuccessOk(message: String) {
        sSuccess
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismissWithAnimation()
                startActivity(Intent(requireActivity(), UserActivity::class.java))
            }
            .show()
    }


    override fun showSuccess(message: String) {
        sSuccess
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismissWithAnimation()
            }
            .show()
    }

    override fun showError(message: String) {
        sError
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismiss()
            }
            .show()
    }

    override fun showAlert(message: String) {
        sAlert
            .setContentText(message)
            .setConfirmText("Ya")
            .setConfirmClickListener {
                it.dismissWithAnimation()
            }
            .setCancelText("Nanti")
            .setCancelClickListener {
                it.dismiss()
            }
            .show()
        sAlert.setCancelable(true)
    }
}