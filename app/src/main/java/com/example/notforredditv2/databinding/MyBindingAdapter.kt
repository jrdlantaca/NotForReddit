package com.example.notforredditv2.databinding

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object MyBindingAdapter {

    @JvmStatic
    @BindingAdapter("app:postThumbnail")
    fun loadImage(imageView : ImageView, thumbnail : String){
        if(thumbnail == "self" || thumbnail == "image"){
            imageView.visibility = View.GONE
        }
        else{
            Glide.with(imageView.context)
                .asBitmap()
                .load(thumbnail)
                .dontAnimate()
                .into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("app:articleThumbnail")
    fun loadArticleImage(imageView : ImageView, thumbnail : String){
        if(thumbnail == "self" || thumbnail == "image"){
            imageView.visibility = View.GONE
        }
        else{
            Glide.with(imageView.context)
                .asBitmap()
                .load(thumbnail)
                .dontAnimate()
                .into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("app:hasSelfText")
    fun setSelfText(textView : TextView, selfText : String){
        if(selfText == ""){
            textView.visibility = View.GONE
        }
        else{
            textView.text = selfText
        }
    }


}