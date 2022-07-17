package com.example.fampaytask.model

import com.google.gson.annotations.SerializedName

data class Card (
    val name: String,
    val title: String? = null,

    @SerializedName("formatted_title")
    val formattedTitle: Formatted? = null,

    val description: String? = null,

    @SerializedName("formatted_description")
    val formattedDescription: Formatted? = null,

    val icon: FamImage? = null,
    val url: String,

    @SerializedName("is_disabled")
    val isDisabled: Boolean,

    @SerializedName("bg_image")
    val bgImage: FamImage? = null,

    @SerializedName( "bg_color")
    val bgColor: String? = null,

    val cta: List<Cta>? = null
)
