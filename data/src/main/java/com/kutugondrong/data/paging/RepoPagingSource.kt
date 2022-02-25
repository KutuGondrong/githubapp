package com.kutugondrong.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kutugondrong.data.model.Repo
import com.kutugondrong.data.model.User
import com.kutugondrong.data.remote.service.GithubApi
import retrofit2.HttpException
import java.io.IOException

class RepoPagingSource(
    private val githubApi: GithubApi,
    private val user: User?,
) : PagingSource<Int, Repo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {

        return try {
            val response =
                if (user == null) arrayListOf() else githubApi.getUserRepos(user.userName)

            val repoList: List<Repo> = response.mapNotNull {
                try {
                    it.toRepo()
                } catch (e: Exception) {
                    null
                }
            }

            LoadResult.Page(
                data = repoList,
                prevKey = null,
                nextKey = null
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        return state.anchorPosition
    }
}