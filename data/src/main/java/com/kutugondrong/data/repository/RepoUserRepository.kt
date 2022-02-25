package com.kutugondrong.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.kutugondrong.data.model.Repo
import com.kutugondrong.data.model.User
import com.kutugondrong.data.paging.RepoPagingSource
import com.kutugondrong.data.remote.service.GithubApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoUserRepository @Inject constructor(
    private val githubApi: GithubApi
) {

    fun getReposByUser(user: User?): LiveData<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(
                pageSize = GithubApi.PAGE_SIZE,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { RepoPagingSource(githubApi, user) }
        ).liveData
    }

}