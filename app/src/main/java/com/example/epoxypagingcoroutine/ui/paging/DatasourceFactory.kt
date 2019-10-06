package com.example.epoxypagingcoroutine.ui.paging

import androidx.paging.DataSource
import com.example.epoxypagingcoroutine.data.GithubApi
import com.example.epoxypagingcoroutine.data.model.Repo
import kotlinx.coroutines.CoroutineScope

class DatasourceFactory(
    username: String,
    api: GithubApi,
    scope: CoroutineScope
) : DataSource.Factory<String, Repo>() {

    val source = GithubPagingDataSource(username, api, scope)
    override fun create(): DataSource<String, Repo> = source
}
