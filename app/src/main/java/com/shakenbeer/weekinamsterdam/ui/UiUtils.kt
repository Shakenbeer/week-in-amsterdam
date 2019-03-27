package com.shakenbeer.weekinamsterdam.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter

object UiUtils {
    @JvmStatic
    @BindingAdapter("android:image_res")
    fun setImageViewResource(imageView: ImageView, resource: Int) {
        imageView.setImageResource(resource)
    }
}