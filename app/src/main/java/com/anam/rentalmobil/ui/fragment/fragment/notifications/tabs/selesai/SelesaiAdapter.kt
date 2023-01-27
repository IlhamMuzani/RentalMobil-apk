package com.anam.rentalmobil.ui.fragment.fragment.notifications.tabs.selesai

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anam.rentalmobil.R
import com.anam.rentalmobil.data.model.Constant
import com.anam.rentalmobil.data.model.transaksi.DataTransaksi
import com.anam.rentalmobil.ui.fragment.fragment.notifications.detailtransaksi.DetailtransaksiActivity
import kotlinx.android.synthetic.main.adapter_selesai.view.*
import kotlin.collections.ArrayList


class SelesaiAdapter (val context: Context, var dataTransaksi: ArrayList<DataTransaksi>,
                      val clickListener: (DataTransaksi, Int, String) -> Unit ):
        RecyclerView.Adapter<SelesaiAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_selesai, parent, false)
    )

    override fun getItemCount() = dataTransaksi.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(dataTransaksi[position])

        holder.view.detailselesai.setOnClickListener {
            Constant.TRANSAKSI_ID = dataTransaksi[position].id!!
            context.startActivity(Intent(context, DetailtransaksiActivity::class.java ))
        }

//        holder.view.txvOptionselesai.setOnClickListener {
//            val popupMenu = PopupMenu(context, holder.view.txvOptionss)
//            popupMenu.inflate(R.menu.menu_options)
//            popupMenu.setOnMenuItemClickListener {
//                when(it.itemId){
//                    R.id.action_delete -> {
//                        Constant.TRANSAKSI_ID = dataTransaksi[position].id!!
//                        clickListener(dataTransaksi[position], position, "Delete")
//                    }
//                }
//                true
//            }
//
//            popupMenu.show()
//            }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bing(dataTransaksi: DataTransaksi) {
            view.txvtransaksiSelesai.text = dataTransaksi.produk.mobil.nama
            view.txv_tanggal.text = dataTransaksi.tanggal
            view.txvKategoriselesai.text = dataTransaksi.produk.kategori
            view.hargaselesai.text = dataTransaksi.harga
        }
    }

    fun setData(newDataTransaksi: List<DataTransaksi>) {
        dataTransaksi.clear()
        dataTransaksi.addAll(newDataTransaksi)
        notifyDataSetChanged()
    }

    fun removetransaksi(position: Int) {
        dataTransaksi.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, dataTransaksi.size)
    }

}