package com.a24i.jobinterview

import android.databinding.BindingAdapter
import android.widget.ImageView

import com.bumptech.glide.Glide

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
                    .placeholder(placeholder)
                    //					.listener(new RequestListener<String, GlideDrawable>()
                    //					{
                    //						@Override
                    //						public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource)
                    //						{
                    //							return false;
                    //						}
                    //
                    //
                    //						@Override
                    //						public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource)
                    //						{
                    //							return false;
                    //						}
                    //					})
                    .into(imageView)
        }
    }
}
