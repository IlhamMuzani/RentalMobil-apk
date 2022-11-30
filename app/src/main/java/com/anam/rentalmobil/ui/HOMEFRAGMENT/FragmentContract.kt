package com.anam.rentalmobil.ui.HOMEFRAGMENT

interface FragmentContract {

    interface View{
        fun initListener()
        fun showMessage(message: String)
    }
}