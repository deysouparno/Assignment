package com.example.fampaytask.model

import com.google.gson.annotations.SerializedName


data class CardGroup (
    val id: Long,
    val name: String,

    @SerializedName("design_type")
    val designType: String,

    val cards: List<Card>,

    @SerializedName("is_scrollable")
    val isScrollable: Boolean,

    val height: Long? = null,

    @SerializedName("card_type")
    val cardType: Long? = null,

    val level: Long? = null
)