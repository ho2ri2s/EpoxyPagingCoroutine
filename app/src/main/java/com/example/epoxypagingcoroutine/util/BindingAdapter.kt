package com.example.epoxypagingcoroutine.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.epoxypagingcoroutine.R

@BindingAdapter("image")
fun ImageView.setImage(url: String) {
    Glide
        .with(this)
        .load(url)
        .error(R.mipmap.ic_launcher)
        .into(this)
}