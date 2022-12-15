package com.anam.rentalmobil.ui.a_FRAGMENT

interface FragmentContract {

    interface View{
        fun initListener()
        fun showMessage(message: String)
    }
}