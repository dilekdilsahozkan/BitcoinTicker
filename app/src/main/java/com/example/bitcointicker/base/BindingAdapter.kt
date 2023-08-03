package com.example.bitcointicker.base

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("coin:src")
fun ImageView.loadImage(mediaUrl: String?) {
    Glide.with(context).clear(this)
    Glide.with(context).load(mediaUrl)
        .dontAnimate()
        .into(this)
}