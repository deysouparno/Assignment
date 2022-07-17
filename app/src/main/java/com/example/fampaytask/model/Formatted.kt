package com.example.fampaytask.model

data class Formatted (
    var text: String,
    val align: String? = null,
    val entities: List<Entity>
)

data class Entity(val text: String, val color: String)
