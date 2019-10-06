package com.example.epoxypagingcoroutine.data

import com.example.epoxypagingcoroutine.data.model.Repo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    @GET("/users/{username}/repos")
    suspend fun getGithubRepos(@Path("username") username: String): Response<List<Repo>>
}