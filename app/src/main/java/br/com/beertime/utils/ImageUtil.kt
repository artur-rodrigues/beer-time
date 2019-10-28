package br.com.beertime.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import br.com.beertime.R
import com.squareup.picasso.Picasso

/**
 * Created by Artur on 28/10/2019.
 */
fun loadImageForList(url: String, imgView: ImageView) {
    loadImageInImageView(url, 60, 60, R.mipmap.place_holder, imgView)
}

fun loadImageForDetail(url: String, imgView: ImageView) {
    loadImageInImageView(url, 300, 300, R.mipmap.place_holder, imgView)
}

fun loadImageInImageView(url: String, width: Int,
                         height: Int, @DrawableRes id: Int,
                         imgView: ImageView) {
    Picasso.get()
        .load(url)
        .resize(width, height)
        .placeholder(id)
        .into(imgView)
}