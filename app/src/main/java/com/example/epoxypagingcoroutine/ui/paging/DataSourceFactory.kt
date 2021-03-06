package com.example.epoxypagingcoroutine.ui.paging

import androidx.paging.DataSource
import com.example.epoxypagingcoroutine.data.GithubApi
import com.example.epoxypagingcoroutine.data.model.Repo
import kotlinx.coroutines.CoroutineScope

class DataSourceFactory(
    private val username: String,
    private val api: GithubApi,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Repo>() {

    override fun create(): DataSource<Int, Repo> = GithubPagingDataSource(username, api, scope)
}
