package com.example.epoxypagingcoroutine.data

import com.example.epoxypagingcoroutine.data.model.Owner
import com.example.epoxypagingcoroutine.data.model.Repo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("/users/{username}/repos")
    suspend fun getGithubRepos(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<List<Repo>>

    @GET("/users/{username}")
    suspend fun getOwner(@Path("username") username: String): Response<Owner>
}
