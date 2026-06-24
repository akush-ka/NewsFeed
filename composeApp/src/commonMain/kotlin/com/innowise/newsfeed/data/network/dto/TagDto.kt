package com.innowise.newsfeed.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
internal class TagDto(
    val id: Long,
    val name: String,
)
