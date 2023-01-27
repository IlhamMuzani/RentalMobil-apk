package com.anam.rentalmobil.ui.fragment.fragment.notifications.detailtransaksi

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.anam.rentalmobil.R
import com.anam.rentalmobil.data.model.Constant
import com.anam.rentalmobil.data.model.rekening.DataRekening
import com.anam.rentalmobil.ui.utils.GlideHelper
import kotlinx.android.synthetic.main.adapter_rekening.view.*
import kotlin.collections.ArrayList


class RekeningAdapter (val context: Context, var dataRekening: ArrayList<DataRekening>,
                       val clickListener: (DataRekening, Int, String) -> Unit ):
    RecyclerView.Adapter<RekeningAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_rekening, parent, false)
    )

    override fun getItemCount() = dataRekening.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(dataRekening[position])

        holder.view.imvcoppy.setOnClickListener {
            val copyManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val copyText = ClipData.newPlainText("text", dataRekening[position].nomor)
            copyManager.setPrimaryClip(copyText)

            Toast.makeText(context, "Nomor rekening berhasil di salin", Toast.LENGTH_SHORT).show()
        }
              GlideHelper.setImage(context, Constant.IP_IMAGE + dataRekening[position].gambar, holder.imvGambar)

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bing(dataRekening: DataRekening) {
            view.txtBank.text = dataRekening.nomor
            view.txtAtasNama.text = dataRekening.nama
        }
        val imvGambar = view.findViewById<ImageView>(R.id.imvBank)
    }

    fun setData(newDataRekening: List<DataRekening>) {
        dataRekening.clear()
        dataRekening.addAll(newDataRekening)
        notifyDataSetChanged()
    }
}