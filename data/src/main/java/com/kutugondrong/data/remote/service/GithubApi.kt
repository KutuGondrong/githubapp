package com.kutugondrong.data.remote.service

import com.kutugondrong.data.remote.model.RepoResponse
import com.kutugondrong.data.remote.model.SearchUserResponse
import com.kutugondrong.data.remote.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    companion object {
        const val PAGE_SIZE = 4
    }

    @GET("search/users?per_page=$PAGE_SIZE")
    suspend fun getUsers(@Query("q") search: String, @Query("page") page: Int): SearchUserResponse

    @GET("users/{userName}")
    suspend fun getDetailUser(@Path("userName") userName: String): UserResponse

    @GET("users/{userName}/repos")
    suspend fun getUserRepos(@Path("userName") userName: String): List<RepoResponse>
}