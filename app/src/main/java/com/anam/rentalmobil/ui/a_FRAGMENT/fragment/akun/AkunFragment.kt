package com.ilham.taspesialisbangunan.ui.home.fragment.Akun

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.anam.rentalmobil.R
import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.Constant
import com.anam.rentalmobil.data.model.user.ResponseUserdetail
import com.anam.rentalmobil.ui.a_FRAGMENT.FragmenttActivity
import com.anam.rentalmobil.ui.passwordbaru.PasswordbaruActivity
import com.anam.rentalmobil.ui.syarat.SyaratActivity
import com.anam.rentalmobil.ui.updateprofil.UpdateprofilActivity
import com.ilham.taspesialisbangunan.ui.utils.MapsHelper
import kotlinx.android.synthetic.main.fragment_akun.*


class AkunFragment : Fragment(), AkunContract.View {

    lateinit var prefsManager: PrefsManager
    lateinit var presenter: AkunPresenter

    lateinit var BtnUbahProfil : RelativeLayout
    lateinit var Btnsyarat : RelativeLayout
    lateinit var BtnTentang : RelativeLayout
    lateinit var Btnpasswordbaru : RelativeLayout
    lateinit var txvNama : TextView
    lateinit var txvNIK : TextView
    lateinit var TxvAlamat : TextView
    lateinit var Txvphone : TextView
    lateinit var TxvGenderlakilaki : TextView
    lateinit var TxvGenderperempuan : TextView
    lateinit var TxvLogout : TextView

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
//        supportActionBar!!.hide()

        MapsHelper.permissionMap(requireContext(), requireActivity())
        BtnUbahProfil = view.findViewById(R.id.btn_ubahProfil)
        Btnsyarat = view.findViewById(R.id.btn_syarat)
        BtnTentang = view.findViewById(R.id.btn_tentang)
        Btnpasswordbaru = view.findViewById(R.id.btn_ubahPassword)
        txvNama= view.findViewById(R.id.txv_nama)
        txvNIK = view.findViewById(R.id.txvNIK)
//        TxvAlamat = view.findViewById(R.id.txvAlamat)
        Txvphone = view.findViewById(R.id.txvPhone)
        TxvGenderlakilaki = view.findViewById(R.id.txvGenderlakilaki)
        TxvGenderperempuan = view.findViewById(R.id.txvGenderperempuan)
        TxvLogout = view.findViewById(R.id.txvLogout)


        BtnUbahProfil.setOnClickListener{
            Constant.USER_ID = prefsManager.prefsId.toLong()
            startActivity(Intent(requireActivity(), UpdateprofilActivity::class.java))
        }

        Btnsyarat.setOnClickListener {
            startActivity(Intent(requireActivity(), SyaratActivity::class.java))
        }

        BtnTentang.setOnClickListener {
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
//        requireActivity().finish()
        startActivity(Intent(requireActivity(), FragmenttActivity::class.java))
    }

    override fun onResult(responseUserdetail: ResponseUserdetail) {
        val akun = responseUserdetail.user
        txvNama.setText(akun!!.nama)
        txvNIK.setText(akun!!.nik)
        Txvphone.setText(akun!!.telp)
        if (responseUserdetail.user.gender == "P"){
            TxvGenderperempuan.visibility = View.VISIBLE
            TxvGenderlakilaki.visibility = View.GONE
        }else{
            TxvGenderlakilaki.visibility = View.VISIBLE
            TxvGenderperempuan.visibility = View.GONE
        }
//        txvAlamat.setText(akun!!.alamat)
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}