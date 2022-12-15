package com.anam.rentalmobil.ui.a_FRAGMENT.fragment.notifications.tabs.menunggu

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

class MenungguFragment : Fragment(), MenungguContract.View {

    lateinit var presenter: MenungguPresenter
    lateinit var menungguAdapter: MenungguAdapter
    lateinit var dataaTransaksi: DataTransaksi
    lateinit var prefsManager: PrefsManager

    lateinit var layoutdatakosong : LinearLayout
    lateinit var rcvMenunggu: RecyclerView
    lateinit var swipeMenunggu: SwipeRefreshLayout

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
        if (prefsManager.prefIsLogin){
            presenter.getStatusmenunggu(prefsManager.prefsId.toLong())
        }
    }

    override fun initFragment(view: View) {
        rcvMenunggu = view.findViewById(R.id.rcvMenunggu)
        swipeMenunggu = view.findViewById(R.id.swipeMenunggu)
        layoutdatakosong = view.findViewById(R.id.layoutdatatidakada)

        menungguAdapter = MenungguAdapter(requireActivity(), arrayListOf()) { dataTransaksi: DataTransaksi, position: Int, type: String ->

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
                if (prefsManager.prefIsLogin){
                    presenter.getStatusmenunggu(prefsManager.prefsId.toLong())
                }
            }
    }

    override fun onloading(loading: Boolean) {
        when (loading) {
            true -> swipeMenunggu.isRefreshing = true
            false -> swipeMenunggu.isRefreshing = false
        }
    }

    override fun onResult(responseTransaksiList: ResponseTransaksiList) {
        if (responseTransaksiList.dataTransaksi != null){
            val transaksi: List<DataTransaksi> = responseTransaksiList.dataTransaksi
            menungguAdapter.setData(transaksi)

            layoutdatakosong.visibility = View.GONE
        }else{
            layoutdatakosong.visibility = View.VISIBLE

        }
    }

    override fun onResultDelete(responseTransaksiUpdate: ResponseTransaksiUpdate) {
        showMessage( responseTransaksiUpdate.message)
    }

    override fun showDialogDelete(dataTransaksi: DataTransaksi, position: Int) {
        val dialog = AlertDialog.Builder(requireActivity())
        dialog.setTitle("Konfirmasi")
        dialog.setMessage("Hapus ${dataTransaksi.produk.mobil.nama}?")

        dialog.setPositiveButton("Hapus"){ dialog, which ->
            presenter.deletetransaksi(Constant.TRANSAKSI_ID)
            menungguAdapter.removetransaksi(position)
            dialog.dismiss()
        }

        dialog.setNegativeButton("Batal"){dialog, which ->
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }


}