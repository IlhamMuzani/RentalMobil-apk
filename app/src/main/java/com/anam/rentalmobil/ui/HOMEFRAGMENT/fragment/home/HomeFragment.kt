package com.ilham.taspesialisbangunan.ui.home.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.anam.rentalmobil.R
import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.data.model.Constant
import com.anam.rentalmobil.data.model.produk.DataProduk
import com.anam.rentalmobil.data.model.produk.ResponseProdukList
import com.anam.rentalmobil.ui.HOMEFRAGMENT.fragment.home.ProdukAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), HomeContract.View {

    lateinit var prefsManager: PrefsManager
    lateinit var presenter: HomePresenter
    lateinit var produk: DataProduk
    lateinit var produkAdapter: ProdukAdapter

    lateinit var RcvProduk : RecyclerView
    lateinit var Swipe : SwipeRefreshLayout
    lateinit var EditSearch : EditText
    lateinit var SPinnerPilihproduk: Spinner

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

        SPinnerPilihproduk = view.findViewById(R.id.categorijenisproduk)
        RcvProduk = view.findViewById(R.id.rcvProduk)
        Swipe = view.findViewById(R.id.swipe)
        EditSearch = view.findViewById(R.id.edtSearch)

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

                presenter.Search(EditSearch.text.toString() )
                true
            } else {
                false
            }
        }

    }

    override fun onLoading(loading: Boolean) {
        when (loading){
            true -> Swipe.isRefreshing = true
            false -> Swipe.isRefreshing = false
        }
    }

    override fun onResult(responseProdukList: ResponseProdukList) {
        val dataProduk: List<DataProduk> = responseProdukList.dataproduk
        produkAdapter.setData(dataProduk)
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    fun spinnercategory() {

        val arrayString = ArrayList<String>()
        arrayString.add("Pilih Kategori")
        arrayString.add("Sewa Mobil")
        arrayString.add("Sewa Travel")

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
                        Constant.PRODUK_NAME = "Pilih Kategori"
//                        presenter.searchProduk("produk" )
                    }
                    1 -> {
                        Constant.PRODUK_ID = 1
                        Constant.PRODUK_NAME = "Sewa Mobil"
//                        presenter.searchProduk("Sewa Mobil" )
                    }
                    2 -> {
                        Constant.PRODUK_ID = 2
                        Constant.PRODUK_NAME = "Sewa Travel"
//                        presenter.searchProduk("Sewa Tarvel" )
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

}