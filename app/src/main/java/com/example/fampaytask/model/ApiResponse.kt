package com.example.fampaytask.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("card_groups") val cardGroups: List<CardGroup>
)
