package com.anam.rentalmobil.ui.fragment.fragment.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anam.rentalmobil.R
import com.anam.rentalmobil.data.model.Constant
import com.anam.rentalmobil.data.model.produk.DataProduk
import com.anam.rentalmobil.ui.fragment.fragment.detailproduk.DetailprodukActivity
import com.anam.rentalmobil.ui.utils.GlideHelper
import kotlinx.android.synthetic.main.adapter_produk.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class ProdukAdapter (val context: Context, var dataProduk: ArrayList<DataProduk>,
                     val clickListener: (DataProduk, Int, String) -> Unit ):
    RecyclerView.Adapter<ProdukAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_produk, parent, false)
    )

    override fun getItemCount() = dataProduk.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(dataProduk[position])

        GlideHelper.setImage(context, Constant.IP_IMAGE +  dataProduk[position].mobil.gambar!!, holder.view.imvImage)

        holder.view.txvDetail.setOnClickListener {
            Constant.PRODUK_ID = dataProduk[position].id!!
            context.startActivity(Intent(context, DetailprodukActivity::class.java ))
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bing(dataproduk: DataProduk) {
            view.txvMobil.text = dataproduk.mobil.nama
            view.txvModel.text = dataproduk.mobil.warna
            view.txvHarga.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(Integer.valueOf(dataproduk.sewa))
        }
    }

    fun setData(newDataProduk: List<DataProduk>) {
        dataProduk.clear()
        dataProduk.addAll(newDataProduk)
        notifyDataSetChanged()
    }
}