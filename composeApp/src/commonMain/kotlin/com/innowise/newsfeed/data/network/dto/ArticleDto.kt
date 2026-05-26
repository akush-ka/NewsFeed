package com.innowise.newsfeed.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDto(
    val id: Long,
    val title: String,
    val description: String = "",
    val url: String = "",
    @SerialName("cover_image")
    val coverImage: String? = null,
    @SerialName("social_image")
    val socialImage: String? = null,
    @SerialName("published_timestamp")
    val publishedTimestamp: String = "",
    @SerialName("reading_time_minutes")
    val readingTimeMinutes: Int = 0,
    @SerialName("tag_list")
    val tagList: List<String> = emptyList(),
    val user: UserDto? = null,
    val organization: OrganizationDto? = null,
)

@Serializable
data class UserDto(
    val name: String = "",
)

@Serializable
data class OrganizationDto(
    val name: String = "",
)
