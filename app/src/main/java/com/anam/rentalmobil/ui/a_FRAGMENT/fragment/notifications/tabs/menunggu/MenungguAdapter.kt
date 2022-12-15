package com.anam.rentalmobil.ui.a_FRAGMENT.fragment.notifications.tabs.menunggu

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.anam.rentalmobil.R
import com.anam.rentalmobil.data.model.Constant
import com.anam.rentalmobil.data.model.transaksi.DataTransaksi
import com.anam.rentalmobil.ui.a_FRAGMENT.fragment.notifications.detailtransaksi.DetailtransaksiActivity
import com.anam.rentalmobil.ui.utils.GlideHelper
import kotlinx.android.synthetic.main.adapter_menunggu.view.*
import kotlin.collections.ArrayList


class MenungguAdapter (val context: Context, var dataTransaksi: ArrayList<DataTransaksi>,
                       val clickListener: (DataTransaksi, Int, String) -> Unit ):
        RecyclerView.Adapter<MenungguAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_menunggu, parent, false)
    )

    override fun getItemCount() = dataTransaksi.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(dataTransaksi[position])

        holder.view.imvTransaksi.setOnClickListener {
            Constant.TRANSAKSI_ID = dataTransaksi[position].id!!
            context.startActivity(Intent(context, DetailtransaksiActivity::class.java ))
        }
        GlideHelper.setImage(context, Constant.IP_IMAGE + dataTransaksi[position].produk.mobil.gambar, holder.ImvTransaksi)

        holder.view.txvOptionss.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.view.txvOptionss)
            popupMenu.inflate(R.menu.menu_options)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.action_delete -> {
                        Constant.TRANSAKSI_ID = dataTransaksi[position].id!!
                        clickListener(dataTransaksi[position], position, "Delete")
                    }
                }
                true
            }

            popupMenu.show()
            }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bing(dataTransaksi: DataTransaksi) {
            view.txvNamaMobil.text = dataTransaksi.produk.mobil.nama
//            view.txvwarna.text = dataTransaksi.produk.mobil.warna
            view.txvkategori.text = dataTransaksi.produk.kategori
        }
        val ImvTransaksi = view.findViewById<ImageView>(R.id.imvTransaksi)
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