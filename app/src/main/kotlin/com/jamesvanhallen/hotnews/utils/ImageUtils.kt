package com.jamesvanhallen.hotnews.utils

import android.widget.ImageView
import com.jamesvanhallen.hotnews.R
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

fun setImage(picasso: Picasso, imageView: ImageView, url: String?) {
    val load: RequestCreator = if (!url.isNullOrEmpty()) {
        picasso.load(url!!)
    } else {
        picasso.load(R.mipmap.ic_launcher)
    }

    load.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
            .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
            .error(R.mipmap.ic_launcher)
            .fit()
            .centerCrop()
            .into(imageView)
}