package com.anam.rentalmobil.ui.a_FRAGMENT

class FragmentPresenter(val view : FragmentContract.View) {

    init {
        view.initListener()
    }
}