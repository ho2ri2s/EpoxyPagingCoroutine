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
) : PageKeyedDataSource<String, Repo>() {
    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, Repo>
    ) {
        scope.launch {
            callApi("1", "$params.requestedLoadSize") { next, repos ->
                callback.onResult(repos, null, next)
            }
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Repo>) {
        scope.launch {
            callApi(params.key, "$params.requestedLoadSize") { next, repos ->
                callback.onResult(repos, next)
            }
        }
    }

    // 途中から読み出すことはないので実装せず
    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Repo>) {
    }

    private suspend fun callApi(
        page: String,
        perPage: String,
        callback: (next: String?, repos: List<Repo>) -> Unit
    ) {
        try {
            val response = api.getGithubRepos(username, page, perPage)
            if (response.isSuccessful) {
                response.body()?.let {
                    var next: String? = null
                    //Headerにnextがあれば次ページを加算
                    response.headers().get("Link")?.let {
                        val regex = Regex("rel=\"next\"")
                        if (regex.containsMatchIn(it)) {
                            next = "${page + 1}"
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
