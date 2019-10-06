package com.example.epoxypagingcoroutine.data

import com.example.epoxypagingcoroutine.data.model.Repo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("/users/{user}/repos")
    suspend fun getGithubRepos(
        @Path("user") username: String,
        @Query("page") page: String,
        @Query("per_page") perPage: String): Response<List<Repo>>
}