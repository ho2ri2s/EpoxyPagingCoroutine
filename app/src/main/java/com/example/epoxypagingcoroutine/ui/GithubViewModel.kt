package com.example.epoxypagingcoroutine.ui

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.epoxypagingcoroutine.data.GithubApi
import com.example.epoxypagingcoroutine.data.model.Owner
import com.example.epoxypagingcoroutine.data.model.Repo
import com.example.epoxypagingcoroutine.ui.paging.DatasourceFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class GithubViewModel @Inject constructor(
    private val api: GithubApi
) : ViewModel() {

    private val _name = MutableLiveData<String>()

    val repos: LiveData<PagedList<Repo>> =
        Transformations.switchMap(_name) { username ->
            val dataSourceFactory = DatasourceFactory(username, api, viewModelScope)

            val config = PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE)
                .setInitialLoadSizeHint(PAGE_SIZE)
                .build()

            LivePagedListBuilder(dataSourceFactory, config).build()
        }

    private val _owner = MutableLiveData<List<Owner>>()
    val owner: LiveData<List<Owner>>
        get() = _owner

    fun setName(username: String) {
        _name.postValue(username)
    }

    fun start() {
        //　今回はここで決め打ち
        val usernames = listOf("ho2ri2s", "googlesamples", "kotlin", "android", "jetbrains")
        viewModelScope.launch {
            val ownerList = ArrayList<Owner>()
            usernames.forEach { username ->
                try {
                    val response = api.getOwner(username)
                    if (response.isSuccessful && response.body() != null) {
                        ownerList.add(response.body()!!)
                    }
                } catch (t: Throwable) {
                    t.printStackTrace()
                }
            }
            _owner.postValue(ownerList)
        }
    }

    companion object {
        private const val PAGE_SIZE = 30
    }
}
