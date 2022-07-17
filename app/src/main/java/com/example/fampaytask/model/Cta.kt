package com.example.fampaytask.model

import com.google.gson.annotations.SerializedName

data class Cta (
    val text: String,

    @SerializedName("bg_color")
    val bgColor: String,

    @SerializedName("text_color")
    val textColor: String,

//    @SerializedName("url_choice")
//    val urlChoice: String?,

    val url: String
)
