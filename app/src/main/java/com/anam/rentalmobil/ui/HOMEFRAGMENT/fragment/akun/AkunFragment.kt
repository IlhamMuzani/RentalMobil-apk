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
import com.anam.rentalmobil.ui.HOMEFRAGMENT.FragmentActivity
import com.anam.rentalmobil.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_akun.*


class AkunFragment : Fragment(), AkunContract.View {

    lateinit var prefsManager: PrefsManager
    lateinit var presenter: AkunPresenter

    lateinit var BtnUbahProfil : RelativeLayout
    lateinit var txvNama : TextView
    lateinit var txvNIK : TextView
    lateinit var TxvAlamat : TextView
    lateinit var Txvphone : TextView
    lateinit var TxvGender : TextView
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

        BtnUbahProfil = view.findViewById(R.id.btn_ubahProfil)
        txvNama= view.findViewById(R.id.txv_nama)
        txvNIK = view.findViewById(R.id.txvNIK)
//        TxvAlamat = view.findViewById(R.id.txvAlamat)
        Txvphone = view.findViewById(R.id.txvPhone)
        TxvGender = view.findViewById(R.id.txvGender)
        TxvLogout = view.findViewById(R.id.txvLogout)


        BtnUbahProfil.setOnClickListener{
            Constant.USER_ID = prefsManager.prefsId.toLong()
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
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
        startActivity(Intent(requireActivity(), FragmentActivity::class.java))
    }

    override fun onResult(responseUserdetail: ResponseUserdetail) {
        val akun = responseUserdetail.user
        txvNama.setText(akun!!.nama)
        txvNIK.setText(akun!!.nik)
        Txvphone.setText(akun!!.telp)
        TxvGender.setText(akun!!.gender)
//        txvAlamat.setText(akun!!.alamat)
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}