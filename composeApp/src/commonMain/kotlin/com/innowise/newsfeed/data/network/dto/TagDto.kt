package com.innowise.newsfeed.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class TagDto(
    val id: Long,
    val name: String,
)
