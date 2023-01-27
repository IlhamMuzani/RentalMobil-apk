package com.anam.rentalmobil.ui.fragment.fragment.notifications.tabs.sudahbayar

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.anam.rentalmobil.ui.fragment.fragment.notifications.tabs.menunggu.MenungguAdapter
import com.anam.rentalmobil.ui.sweetalert.SweetAlertDialog

class SudahbayarFragment : Fragment(), SudahbayarContract.View {

    lateinit var presenter: SudahbayarPresenter
    lateinit var sudahbayarAdapter: MenungguAdapter
    lateinit var dataaTransaksi: DataTransaksi
    lateinit var prefsManager: PrefsManager

    lateinit var rcvSudahbayar: RecyclerView
    lateinit var swipeSudahbayar: SwipeRefreshLayout
    lateinit var layoutkosong: LinearLayout
    lateinit var lin_refresh: LinearLayout

    lateinit var sLoading: SweetAlertDialog
    lateinit var sAlert: SweetAlertDialog
    lateinit var sError: SweetAlertDialog
    lateinit var sSuccess: SweetAlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sudahbayar, container, false)

        presenter = SudahbayarPresenter(this)
        prefsManager = PrefsManager(requireActivity())

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        if (prefsManager.prefIsLogin){
            presenter.getStatussudahbayar(prefsManager.prefsId.toLong())
            lin_refresh.visibility = View.VISIBLE
        }else{
            lin_refresh.visibility = View.GONE
        }
    }

    override fun initFragment(view: View) {
        sLoading = SweetAlertDialog(requireActivity(), SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(requireActivity(), SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = SweetAlertDialog(requireActivity(), SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal")
        sAlert = SweetAlertDialog(requireActivity(), SweetAlertDialog.WARNING_TYPE).setTitleText("Perhatian!")

        rcvSudahbayar = view.findViewById(R.id.rcvSudahbayar)
        swipeSudahbayar = view.findViewById(R.id.swipeSudahbayar)
        layoutkosong = view.findViewById(R.id.layoutdatakosong)
        lin_refresh = view.findViewById(R.id.lin_refreshbayar)

        sudahbayarAdapter = MenungguAdapter(requireActivity(), arrayListOf()) { dataTransaksi: DataTransaksi, position: Int, type: String ->

            dataaTransaksi = dataTransaksi

            when (type) {
                "Delete" -> showDialogDelete(dataTransaksi, position)
            }

        }
        rcvSudahbayar.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = sudahbayarAdapter
        }

        swipeSudahbayar.setOnRefreshListener {
            if (prefsManager.prefIsLogin){
                presenter.getStatussudahbayar(prefsManager.prefsId.toLong())
            }
        }

        lin_refresh.setOnClickListener {
            if (prefsManager.prefIsLogin) {
                showSuccess("")
                presenter.getStatussudahbayar(prefsManager.prefsId.toLong())
            }
        }
    }

    override fun onloading(loading: Boolean) {
        when (loading) {
            true -> swipeSudahbayar.isRefreshing = true
            false -> swipeSudahbayar.isRefreshing = false
        }
    }

    override fun onloadingswet(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setContentText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResult(responseTransaksiList: ResponseTransaksiList) {
        if (responseTransaksiList.status){
        val transaksi: List<DataTransaksi> = responseTransaksiList.dataTransaksi
        sudahbayarAdapter.setData(transaksi)

            layoutkosong.visibility = View.GONE
        }else {
            layoutkosong.visibility = View.VISIBLE
            rcvSudahbayar.visibility = View.GONE
        }
    }

    override fun onResultDelete(responseTransaksiUpdate: ResponseTransaksiUpdate) {
        showSuccess( responseTransaksiUpdate.message)
    }

    override fun showDialogDelete(dataTransaksi: DataTransaksi, position: Int) {
        val dialog = AlertDialog.Builder(requireActivity())
        dialog.setTitle("Konfirmasi")
        dialog.setMessage("Hapus ${dataTransaksi.produk.mobil.nama}?")

        dialog.setPositiveButton("Hapus"){ dialog, which ->
            presenter.deletetransaksi(Constant.TRANSAKSI_ID)
            sudahbayarAdapter.removetransaksi(position)
            dialog.dismiss()
        }

        dialog.setNegativeButton("Batal"){dialog, which ->
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
//                finish()
//                startActivity(Intent(requireActivity(), UserActivity::class.java))
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