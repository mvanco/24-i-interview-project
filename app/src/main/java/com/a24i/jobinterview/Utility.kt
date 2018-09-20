package com.a24i.jobinterview

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

object Utility {

    /**
     * Path to image with format "/kqjL17yufvn9OVLyXYpvtyrFfak.jpg"
     */
    @BindingAdapter("image_url")
    @JvmStatic
    fun setImageUrl(imageView: ImageView, url: String?) {
        loadImage(imageView, R.drawable.ic_photo, url)
    }


    @JvmStatic
    private fun loadImage(imageView: ImageView?, placeholder: Int, url: String?) {
        if (imageView != null && imageView.context != null && url != null) {
            Glide.with(imageView.context)
                    .load(JobInterviewConfig.MOVIE_DB_IMAGE_BASE_URL + url)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(placeholder)
                    .into(imageView)
        } else {
            Glide.clear(imageView)
        }
    }
}
