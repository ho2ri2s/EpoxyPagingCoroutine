package com.example.epoxypagingcoroutine.data.model

import com.squareup.moshi.Json

data class Repo (
    val owner: Owner,
    @Json(name = "name")
    val repoName: String,
    @Json(name = "html_url")
    val url: String,
    @Json(name = "stargazers_count")
    val star: String
)