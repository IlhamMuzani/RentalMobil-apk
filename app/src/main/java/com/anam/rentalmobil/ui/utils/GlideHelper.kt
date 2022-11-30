package com.anam.rentalmobil.ui.utils

import android.content.Context
import android.widget.ImageView
import com.anam.rentalmobil.R
import com.bumptech.glide.Glide

class GlideHelper {

    companion object {

        fun setImage(context: Context, urlImage: String, imageView: ImageView){
            Glide.with(context)
                .load(urlImage)
                .centerCrop()
                .placeholder(R.drawable.img_no_image)
                .error(R.drawable.img_no_image)
                .into(imageView)
        }
    }
}