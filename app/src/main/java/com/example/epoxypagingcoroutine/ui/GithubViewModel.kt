package com.example.epoxypagingcoroutine.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.epoxypagingcoroutine.data.GithubApi
import com.example.epoxypagingcoroutine.data.model.Owner
import com.example.epoxypagingcoroutine.data.model.Repo
import kotlinx.coroutines.launch
import javax.inject.Inject

class GithubViewModel @Inject constructor(
    private val api: GithubApi
) : ViewModel() {

    private val _repo = MutableLiveData<List<Repo>>()
    val repo: LiveData<List<Repo>>
        get() = _repo

    private val _owner = MutableLiveData<Owner>()
    val owner: LiveData<Owner>
        get() = _owner

    //"googlesamples", "android", "kotlin", "jetbrains"
    fun getGithubRepos() = viewModelScope.launch {
        val owners = listOf("ho2ri2s")
        owners.forEach { owner ->
            try {
                val response = api.getGithubRepos(owner, "1", "50")
                if (response.isSuccessful) {
                    val repos: List<Repo>? = response.body()
                    _repo.postValue(repos)
                    _owner.postValue(repos?.get(0)?.owner)
                }
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }
}
