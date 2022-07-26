package com.android.scidtest.data.network.classes

import com.google.gson.annotations.SerializedName

data class Link(
    @SerializedName("url") val url: String?,
    @SerializedName("label") val label: String,
    @SerializedName("active") val active: Boolean,
)
