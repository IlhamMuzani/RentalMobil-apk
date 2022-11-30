package com.anam.rentalmobil.ui.HOMEFRAGMENT.fragment.notifications.tabs.sewa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.anam.rentalmobil.R
class SewaFragment : Fragment() {

    lateinit var rcvSewa: RecyclerView
    lateinit var swipeSewa: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sewa, container, false)


        return view
    }


}