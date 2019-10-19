package com.example.epoxypagingcoroutine.ui

import androidx.lifecycle.*
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.epoxypagingcoroutine.data.GithubApi
import com.example.epoxypagingcoroutine.data.model.Owner
import com.example.epoxypagingcoroutine.data.model.Repo
import com.example.epoxypagingcoroutine.ui.paging.DataSourceFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class GithubViewModel @Inject constructor(
    private val api: GithubApi
) : ViewModel() {

    private val _name = MutableLiveData<String>()

    val repos: LiveData<PagedList<Repo>> =
        switchMap(_name) { username ->
            val dataSourceFactory = DataSourceFactory(username, api, viewModelScope)

            val config = PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE)
                .setInitialLoadSizeHint(PAGE_SIZE)
                .build()

            LivePagedListBuilder(dataSourceFactory, config).build()
        }

    private val _owner = MutableLiveData<List<Owner>>()
    val owner: LiveData<List<Owner>>
        get() = _owner

    fun setUsername(username: String) = viewModelScope.launch {
        _name.postValue(username)
    }

    fun getOwner() {
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
            setUsername(usernames[0])
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
