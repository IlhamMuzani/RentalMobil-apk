package com.anam.rentalmobil.ui.HOMEFRAGMENT

class FragmentPresenter(val view : FragmentContract.View) {

    init {
        view.initListener()
    }
}