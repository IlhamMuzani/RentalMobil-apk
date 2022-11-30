package com.ilham.taspesialisbangunan.ui.home.fragment.notifications

interface NotificationsContract {

   interface View{
       fun initFragment(view: android.view.View)
       fun showMessage(message: String)
   }

}