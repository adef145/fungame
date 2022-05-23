package com.adefruandta.fungame

import android.widget.ImageView
import androidx.core.content.ContextCompat
import coil.dispose
import coil.load

interface ImageRendering {

    fun render(imageView: ImageView)
}

class LocalImageRendering(
    private val resId: Int
) : ImageRendering {

    override fun render(imageView: ImageView) {
        imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, resId))
    }
}

class RemoteImageRendering(private val url: String) : ImageRendering {

    override fun render(imageView: ImageView) {
        imageView.dispose()
        imageView.load(url) {
            placeholder(R.drawable.placeholder)
        }
    }
}