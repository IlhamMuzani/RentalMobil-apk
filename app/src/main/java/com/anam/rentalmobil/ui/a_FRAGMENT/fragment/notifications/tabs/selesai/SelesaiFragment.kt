package com.anam.rentalmobil.ui.a_FRAGMENT.fragment.notifications.tabs.selesai

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
import com.anam.rentalmobil.ui.a_FRAGMENT.fragment.notifications.tabs.menunggu.MenungguAdapter

class SelesaiFragment : Fragment(), SelesaiContract.View {

    lateinit var presenter: SelesaiPresenter
    lateinit var selesaiAdapter: SelesaiAdapter
    lateinit var dataaTransaksi: DataTransaksi
    lateinit var prefsManager: PrefsManager

    lateinit var rcvSelesai: RecyclerView
    lateinit var swipeSelesai: SwipeRefreshLayout
    lateinit var layoutkosong: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_selesai, container, false)

        presenter = SelesaiPresenter(this)
        prefsManager = PrefsManager(requireActivity())

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        if (prefsManager.prefIsLogin) {
            presenter.getSelesai(prefsManager.prefsId.toLong())
        }
    }

    override fun initFragment(view: View) {
        rcvSelesai = view.findViewById(R.id.rcvSelesai)
        swipeSelesai = view.findViewById(R.id.swipeSelesai)
        layoutkosong = view.findViewById(R.id.layoutdatakosongselesai)

        selesaiAdapter = SelesaiAdapter(
            requireActivity(),
            arrayListOf()
        ) { dataTransaksi: DataTransaksi, position: Int, type: String ->

            dataaTransaksi = dataTransaksi

            when (type) {
                "Delete" -> showDialogDelete(dataTransaksi, position)
            }

        }
        rcvSelesai.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = selesaiAdapter
        }

        swipeSelesai.setOnRefreshListener {
            if (prefsManager.prefIsLogin) {
                presenter.getSelesai(prefsManager.prefsId.toLong())
            }
        }
    }

    override fun onloading(loading: Boolean) {
        when (loading) {
            true -> swipeSelesai.isRefreshing = true
            false -> swipeSelesai.isRefreshing = false
        }
    }

    override fun onResult(responseTransaksiList: ResponseTransaksiList) {
        if (responseTransaksiList.status == true) {
            val transaksi: List<DataTransaksi> = responseTransaksiList.dataTransaksi
            selesaiAdapter.setData(transaksi)

            layoutkosong.visibility = View.GONE
        } else {
            layoutkosong.visibility = View.VISIBLE
            rcvSelesai.visibility = View.GONE
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onResultDelete(responseTransaksiUpdate: ResponseTransaksiUpdate) {
        showMessage(responseTransaksiUpdate.message)
    }

    override fun showDialogDelete(dataTransaksi: DataTransaksi, position: Int) {
        val dialog = AlertDialog.Builder(requireActivity())
        dialog.setTitle("Konfirmasi")
        dialog.setMessage("Hapus ${dataTransaksi.produk.mobil.nama}?")

        dialog.setPositiveButton("Hapus") { dialog, which ->
            presenter.deletetransaksi(Constant.TRANSAKSI_ID)
            selesaiAdapter.removetransaksi(position)
            dialog.dismiss()
        }

        dialog.setNegativeButton("Batal") { dialog, which ->
            dialog.dismiss()
        }

        dialog.show()
    }

}