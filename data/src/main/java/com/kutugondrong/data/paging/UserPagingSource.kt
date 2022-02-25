package com.kutugondrong.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kutugondrong.data.model.User
import com.kutugondrong.data.remote.model.SearchUserResponse
import com.kutugondrong.data.remote.service.GithubApi
import retrofit2.HttpException
import java.io.IOException

class UserPagingSource(
    private val githubApi: GithubApi,
    private val search: String
) : PagingSource<Int, User>() {

    private  val pageIndex = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val position = params.key ?: pageIndex

        return try {
            val response = if(search.isNullOrEmpty()) {
                SearchUserResponse(0, arrayListOf())
            } else {githubApi.getUsers(search, position)}

            val userList: List<User> = response.users.mapNotNull {
                try {
                    val user = githubApi.getDetailUser(it.userName.toString())
                    user.toUser()
                } catch (e: Exception) {
                    null
                }
            }
            LoadResult.Page(
                data = userList,
                prevKey = if (position == pageIndex) null else position - 1,
                nextKey = if (userList.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition
    }
}