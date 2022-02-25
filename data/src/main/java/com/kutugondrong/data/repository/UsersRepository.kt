package com.kutugondrong.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.kutugondrong.data.model.User
import com.kutugondrong.data.paging.UserPagingSource
import com.kutugondrong.data.remote.service.GithubApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(
    private val githubApi: GithubApi) {

    fun searchUser(search: String): LiveData<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = GithubApi.PAGE_SIZE,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UserPagingSource(githubApi, search) }
        ).liveData
    }

}