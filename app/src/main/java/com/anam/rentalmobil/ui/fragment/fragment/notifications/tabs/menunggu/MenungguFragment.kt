package com.anam.rentalmobil.ui.fragment.fragment.notifications.tabs.menunggu

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.anam.rentalmobil.R
import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.Constant
import com.anam.rentalmobil.data.model.transaksi.DataTransaksi
import com.anam.rentalmobil.data.model.transaksi.ResponseTransaksiList
import com.anam.rentalmobil.data.model.transaksi.ResponseTransaksiUpdate
import com.anam.rentalmobil.ui.daftar.DaftarActivity
import com.anam.rentalmobil.ui.sweetalert.SweetAlertDialog

class MenungguFragment : Fragment(), MenungguContract.View {

    lateinit var presenter: MenungguPresenter
    lateinit var menungguAdapter: MenungguAdapter
    lateinit var dataaTransaksi: DataTransaksi
    lateinit var prefsManager: PrefsManager

    lateinit var layoutdatakosong: LinearLayout
    lateinit var rcvMenunggu: RecyclerView
    lateinit var swipeMenunggu: SwipeRefreshLayout
    lateinit var  liner_refresh : LinearLayout
    lateinit var layoutbelumlogin : LinearLayout
    lateinit var gambarKosong : ImageView
    lateinit var logindulu : ImageView

    lateinit var sLoading: SweetAlertDialog
    lateinit var sAlert: SweetAlertDialog
    lateinit var sError: SweetAlertDialog
    lateinit var sSuccess: SweetAlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menunggu, container, false)

        presenter = MenungguPresenter(this)
        prefsManager = PrefsManager(requireActivity())

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        if (prefsManager.prefIsLogin) {
            presenter.getStatusmenunggu(prefsManager.prefsId.toLong())
            liner_refresh.visibility = View.VISIBLE
            layoutbelumlogin.visibility = View.GONE
        }else{
            layoutbelumlogin.visibility = View.VISIBLE
            liner_refresh.visibility = View.GONE
        }
    }

    override fun initFragment(view: View) {
        sLoading = SweetAlertDialog(requireActivity(), SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(requireActivity(), SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = SweetAlertDialog(requireActivity(), SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal")
        sAlert = SweetAlertDialog(requireActivity(), SweetAlertDialog.WARNING_TYPE).setTitleText("Perhatian!")

        rcvMenunggu = view.findViewById(R.id.rcvMenunggu)
        swipeMenunggu = view.findViewById(R.id.swipeMenunggu)
        layoutdatakosong = view.findViewById(R.id.layoutdatatidakada)
        liner_refresh = view.findViewById(R.id.liner_refresh)
        gambarKosong = view.findViewById(R.id.gambarkosong)
        logindulu = view.findViewById(R.id.gambarkosonglogin)
        layoutbelumlogin = view.findViewById(R.id.layoutkosongLogin)

        menungguAdapter = MenungguAdapter(
            requireActivity(),
            arrayListOf()
        ) { dataTransaksi: DataTransaksi, position: Int, type: String ->

            dataaTransaksi = dataTransaksi

            when (type) {
                "Delete" -> showDialogDelete(dataTransaksi, position)
            }
        }
        rcvMenunggu.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = menungguAdapter
        }

        swipeMenunggu.setOnRefreshListener {
            if (prefsManager.prefIsLogin) {
                presenter.getStatusmenunggu(prefsManager.prefsId.toLong())
            }
        }

        liner_refresh.setOnClickListener {
            if (prefsManager.prefIsLogin) {
                showSuccess("")
                presenter.getStatusmenunggu(prefsManager.prefsId.toLong())
            }
        }

        gambarKosong.setOnClickListener {
            showError("Data Tidak Ada !!")
        }

        logindulu.setOnClickListener {
            startActivity(Intent(requireActivity(), DaftarActivity::class.java))
        }
    }

    override fun onloading(loading: Boolean) {
        when (loading) {
            true -> swipeMenunggu.isRefreshing = true
            false -> swipeMenunggu.isRefreshing = false
        }
    }

    override fun onloadingswet(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setContentText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResult(responseTransaksiList: ResponseTransaksiList) {
        if (responseTransaksiList.status) {
            val transaksi: List<DataTransaksi> = responseTransaksiList.dataTransaksi
            menungguAdapter.setData(transaksi)
            layoutdatakosong.visibility = View.GONE
            rcvMenunggu.visibility = View.VISIBLE
        }else{
            layoutdatakosong.visibility = View.VISIBLE
            rcvMenunggu.visibility = View.GONE
        }
    }

    override fun onResultDelete(responseTransaksiUpdate: ResponseTransaksiUpdate) {
        showSuccessOk(responseTransaksiUpdate.message)
    }

    override fun showDialogDelete(dataTransaksi: DataTransaksi, position: Int) {
        val dialog = AlertDialog.Builder(requireActivity())
        dialog.setTitle("Konfirmasi")
        dialog.setMessage("Hapus ${dataTransaksi.produk.mobil.nama}?")

        dialog.setPositiveButton("Hapus") { dialog, which ->
            presenter.deletetransaksi(Constant.TRANSAKSI_ID)
            menungguAdapter.removetransaksi(position)
            dialog.dismiss()
        }

        dialog.setNegativeButton("Batal") { dialog, which ->
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun showSuccessOk(message: String) {
        sSuccess
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismissWithAnimation()
            }
            .show()
    }


    override fun showSuccess(message: String) {
        sSuccess
            .setContentText(message)
            .setConfirmText("OK")
            .setConfirmClickListener {
                it.dismiss()
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
//                startPhoneNumberVerification(phone)
            }
            .setCancelText("Nanti")
            .setCancelClickListener {
                it.dismiss()
            }
            .show()
        sAlert.setCancelable(true)
    }

}