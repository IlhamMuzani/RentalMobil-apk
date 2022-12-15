package com.anam.rentalmobil.ui.a_FRAGMENT

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.anam.rentalmobil.R
import com.anam.rentalmobil.data.database.PrefsManager
import com.anam.rentalmobil.ui.daftar.DaftarActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ilham.taspesialisbangunan.ui.home.fragment.Akun.AkunFragment
import com.ilham.taspesialisbangunan.ui.home.fragment.home.HomeFragment
import com.ilham.taspesialisbangunan.ui.home.fragment.notifications.NotificationsFragment

class FragmenttActivity : AppCompatActivity() {

    val fragmentHome: Fragment = HomeFragment()
    val fragmentNotifications: Fragment = NotificationsFragment()
    val fragmentAkun: Fragment = AkunFragment()
    val fm: FragmentManager = supportFragmentManager
    var active: Fragment = fragmentHome

    lateinit var menu: Menu
    lateinit var menuItem: MenuItem
    lateinit var bottomNavigationView: BottomNavigationView


    lateinit var prefsManager: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setUpBottomNav()


        prefsManager = PrefsManager(this)
    }

    fun setUpBottomNav() {
        fm.beginTransaction().add(R.id.container, fragmentHome).show(fragmentHome).commit()
        fm.beginTransaction().add(R.id.container, fragmentNotifications).hide(fragmentNotifications).commit()
        fm.beginTransaction().add(R.id.container, fragmentAkun).hide(fragmentAkun).commit()

        bottomNavigationView = findViewById(R.id.nav_view)
        menu = bottomNavigationView.menu
        menuItem = menu.getItem(0)
        menuItem.isChecked = true

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.navigation_home -> {
                    callFragment(0, fragmentHome)
                }

                R.id.navigation_notifications -> {
                    callFragment(1, fragmentNotifications)
                }

                R.id.navigation_akun -> {
                    if (prefsManager.prefIsLogin){

                    callFragment(2, fragmentAkun)
                    }
                    else {
                        startActivity(Intent(this, DaftarActivity::class.java))
                    }
                }
            }
            false
        }
    }

    fun callFragment(int: Int, fragment: Fragment) {
        menuItem = menu.getItem(int)
        menuItem.isChecked = true
        fm.beginTransaction().hide(active).show(fragment).commit()
        active = fragment
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (prefsManager.prefIsLogin) {
            startActivity(Intent(this, FragmenttActivity::class.java))
        }
    }
}