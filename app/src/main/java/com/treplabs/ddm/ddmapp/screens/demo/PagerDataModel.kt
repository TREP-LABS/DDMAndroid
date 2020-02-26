package com.treplabs.ddm.ddmapp.screens.demo

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PagerDataModel(
    @StringRes val title: Int, @StringRes val description: Int,
    @DrawableRes val illustrationGraphic: Int
) : Parcelable
