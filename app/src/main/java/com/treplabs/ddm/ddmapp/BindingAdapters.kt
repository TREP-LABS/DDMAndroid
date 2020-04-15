package com.treplabs.ddm.ddmapp

import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.transform.CircleCropTransformation
import com.google.firebase.auth.FirebaseUser
import com.treplabs.ddm.R
import com.treplabs.ddm.extensions.firstName
import com.treplabs.ddm.extensions.lastName


@BindingAdapter("imageUrl")
fun bindUUserImage(imageView: ImageView, uri: Uri?) {
    imageView.load(uri.toString()) {
        placeholder(R.drawable.user_profile_avatar)
        transformations(CircleCropTransformation())
        crossfade(true)
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
