package com.example.fampaytask.model

import com.google.gson.annotations.SerializedName

data class FamImage(
    @SerializedName("image_type")
    val imageType: String,

    @SerializedName("image_url")
    val imageURL: String,

    @SerializedName("aspect_ratio")
    val aspectRatio: Double? = null
)
