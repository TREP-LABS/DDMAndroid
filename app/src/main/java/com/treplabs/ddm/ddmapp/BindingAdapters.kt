package com.treplabs.ddm.ddmapp

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.transform.CircleCropTransformation
import com.google.firebase.auth.FirebaseUser
import com.treplabs.ddm.R
import com.treplabs.ddm.extensions.firstName
import com.treplabs.ddm.extensions.lastName
import timber.log.Timber


@BindingAdapter("imageUrl")
fun bindUserImage(imageView: ImageView, url: String?) {
    Timber.d("Image url is $url")
    url?.let {
        imageView.load(it) {
            placeholder(R.drawable.user_profile_avatar)
            transformations(CircleCropTransformation())
            crossfade(true)
        }
    }
}

@BindingAdapter("firstName")
fun bindUserFirstName(textView: TextView, user: FirebaseUser?) {
    textView.text = user?.firstName()
}

@BindingAdapter("lastName")
fun bindUserLastName(textView: TextView, user: FirebaseUser?) {
    textView.text = user?.lastName()
}
