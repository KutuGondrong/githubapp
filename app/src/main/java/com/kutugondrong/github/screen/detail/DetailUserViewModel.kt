package com.kutugondrong.github.screen.detail

import androidx.lifecycle.*
import androidx.paging.PagingData
import com.kutugondrong.data.model.Repo
import com.kutugondrong.data.model.User
import com.kutugondrong.data.repository.RepoUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailUserViewModel @Inject constructor(
    private val repository: RepoUserRepository
) : ViewModel() {

    var repos: LiveData<PagingData<Repo>> = repository.getReposByUser(null)

    fun getRepos(user: User) {
        repos = repository.getReposByUser(user)
    }

}