package com.example.epoxypagingcoroutine.ui.paging

import androidx.paging.PageKeyedDataSource
import com.example.epoxypagingcoroutine.data.GithubApi
import com.example.epoxypagingcoroutine.data.model.Repo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class GithubPagingDataSource(
    private val username: String,
    private val api: GithubApi,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Repo>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Repo>
    ) {
        scope.launch {
            callApi(1, params.requestedLoadSize) { next, repos ->
                callback.onResult(repos, null, next)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {
        scope.launch {
            callApi(params.key, params.requestedLoadSize) { next, repos ->
                callback.onResult(repos, next)
            }
        }
    }

    // 途中から読み出すことはないので実装せず
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {
    }

    private suspend fun callApi(
        page: Int,
        perPage: Int,
        callback: (next: Int?, repos: List<Repo>) -> Unit
    ) {
        try {
            val response = api.getGithubRepos(username, page, perPage)
            if (response.isSuccessful) {
                response.body()?.let {
                    var next: Int? = null
                    //Headerにnextがあれば次ページを加算
                    response.headers().get("Link")?.let { value ->
                        val regex = Regex("rel=\"next\"")
                        if (regex.containsMatchIn(value)) {
                            next = page + 1
                        }
                    }
                    callback(next, it)
                }
            }
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }
}
