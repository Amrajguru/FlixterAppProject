package com.example.flixterapp

import android.support.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Keep
@Serializable
data class SearchNewsResponse(
    @SerialName("results")
    val response: Movies?
)

@Keep
@Serializable
data class Movies(
    @SerialName("overview")
    var overview: String?,
    @SerialName("release_date")
    var rdate: String? = null,
    @SerialName("vote_average")
    var voteaverage: String? = null,
    @SerialName("multimedia")
    val multimedia: List<MultiMedia>?
)   : java.io.Serializable {
    val mediaImageUrl = "https://www.nytimes.com/${multimedia?.firstOrNull { it.url != null }?.url ?: ""}"
}

@Keep
@Serializable
data class MultiMedia(
    @SerialName("poster_path")
    val url: String?
) : java.io.Serializable