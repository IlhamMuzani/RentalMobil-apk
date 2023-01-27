package com.anam.rentalmobil.ui.fragment.fragment.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.anam.rentalmobil.R
import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.Constant
import com.anam.rentalmobil.data.model.produk.DataProduk
import com.anam.rentalmobil.data.model.produk.ResponseProdukList
import com.anam.rentalmobil.ui.fragment.UserActivity

import com.anam.rentalmobil.ui.sweetalert.SweetAlertDialog

class HomeFragment : Fragment(), HomeContract.View {

    lateinit var prefsManager: PrefsManager
    lateinit var presenter: HomePresenter
    lateinit var produk: DataProduk
    lateinit var produkAdapter: ProdukAdapter

    lateinit var RcvProduk : RecyclerView
    lateinit var Swipe : SwipeRefreshLayout
    lateinit var EditSearch : EditText
    lateinit var SPinnerPilihproduk: Spinner
    lateinit var imvKategori : ImageView

    private lateinit var sLoading: SweetAlertDialog
    private lateinit var sSuccess: SweetAlertDialog
    private lateinit var sError: SweetAlertDialog
    private lateinit var sAlert: SweetAlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        presenter = HomePresenter(this)
        prefsManager = PrefsManager(requireActivity())

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.getProduk()
        spinnercategory()
    }

    override fun initFragment(view: View) {

        sLoading = SweetAlertDialog(requireActivity(), SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = SweetAlertDialog(requireActivity(), SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = SweetAlertDialog(requireActivity(), SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal !")
        sAlert = SweetAlertDialog(requireActivity(), SweetAlertDialog.WARNING_TYPE).setTitleText("Peringatan !")


        SPinnerPilihproduk = view.findViewById(R.id.categorijenisproduk)
        RcvProduk = view.findViewById(R.id.rcvProduk)
        Swipe = view.findViewById(R.id.swipe)
        EditSearch = view.findViewById(R.id.edtSearch)
        imvKategori = view.findViewById(R.id.imvkategori)

        produkAdapter = ProdukAdapter(requireActivity(), arrayListOf()){
            dataProduk: DataProduk, position: Int, type: String ->
            Constant.PRODUK_ID = dataProduk.id!!

            produk = dataProduk
        }

        RcvProduk.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = produkAdapter
        }

        Swipe.setOnRefreshListener {
            presenter.getProduk()
        }

        EditSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                Constant.KEYWORD = EditSearch.text.toString()
                presenter.Searchproduk(Constant.KEYWORD, Constant.KATEGORI_NAME)
                true
            } else {
                false
            }
        }

    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading){
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun onResult(responseProdukList: ResponseProdukList) {
        val dataProduk: List<DataProduk> = responseProdukList.dataproduk
        if (responseProdukList.status) {
            produkAdapter.setData(dataProduk)
        } else {
            showError(responseProdukList.message)
        }
    }

    override fun showSuccesOk(message: String) {
        sSuccess
            .setContentText(message)
            .setConfirmText("Ok")
            .setConfirmClickListener {
                it.dismissWithAnimation()
                startActivity(Intent(requireActivity(), UserActivity::class.java))
            }.show()
    }

    override fun showSucces(message: String) {
        sSuccess
            .setContentText(message)
            .setConfirmText("Ok")
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
            .setConfirmText("Ya")
            .setConfirmClickListener {
                it.dismissWithAnimation()
            }
            .setConfirmText("Nanti")
            .setConfirmClickListener {
                it.dismiss()
            }.show()
        sAlert.setCancelable(true)
    }

    fun spinnercategory() {

        val arrayString = ArrayList<String>()
        arrayString.add("All Item")
        arrayString.add("Mobil Rental")
        arrayString.add("Mobil Tour")

        val adapter = ArrayAdapter(requireContext(), R.layout.item_spinner, arrayString.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SPinnerPilihproduk.adapter = adapter
        val selection = adapter.getPosition(Constant.PRODUK_NAME)
        SPinnerPilihproduk.setSelection(selection)
        SPinnerPilihproduk.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        Constant.PRODUK_ID = 0
                        Constant.PRODUK_NAME = "All Item"
                        presenter.Searchproduk(Constant.KEYWORD, Constant.KATEGORI_NAME)
                    }
                    1 -> {
                        Constant.PRODUK_ID = 1
                        Constant.PRODUK_NAME = "Mobil Rental"
                        presenter.Searchproduk(Constant.KEYWORD, "rental")
                    }
                    2 -> {
                        Constant.PRODUK_ID = 2
                        Constant.PRODUK_NAME = "Mobil Tour"
                        presenter.Searchproduk(Constant.KEYWORD,"tour" )
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

}