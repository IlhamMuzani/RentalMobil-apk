package com.ilham.taspesialisbangunan.ui.home.fragment.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.anam.rentalmobil.R
import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.ui.HOMEFRAGMENT.fragment.notifications.ViewPagerAdapter
import com.anam.rentalmobil.ui.HOMEFRAGMENT.fragment.notifications.tabs.menunggu.MenungguFragment
import com.anam.rentalmobil.ui.HOMEFRAGMENT.fragment.notifications.tabs.sewa.SewaFragment
import com.google.android.material.tabs.TabLayout

class NotificationsFragment : Fragment(), NotificationsContract.View {

    lateinit var prefsManager: PrefsManager
    lateinit var presenter: NotificationsPresenter

    lateinit var viewpager : ViewPager
    lateinit var btn_tabs : TabLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)

        presenter = NotificationsPresenter(this)
        prefsManager = PrefsManager(requireActivity())


        initFragment(view)

        return view
    }

    override fun initFragment(view: View) {

        viewpager = view.findViewById(R.id.btn_viewpager)
        btn_tabs = view.findViewById(R.id.btn_tabs)

        val adapter = ViewPagerAdapter(requireActivity().supportFragmentManager)

        adapter.addFragment(MenungguFragment(), "Menunggu")
        adapter.addFragment(SewaFragment(), "Sewa")
        viewpager.adapter = adapter
        btn_tabs.setupWithViewPager(viewpager)

        btn_tabs.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_access_jam)
        btn_tabs.getTabAt(1)!!.setIcon(R.drawable.dikonfirmasi2)

    }

    override fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}