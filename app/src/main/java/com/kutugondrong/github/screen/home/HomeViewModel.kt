package com.kutugondrong.github.screen.home

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kutugondrong.data.model.User
import com.kutugondrong.data.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UsersRepository
) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val users: LiveData<PagingData<User>> = currentQuery.switchMap { queryString ->
        repository.searchUser(queryString).cachedIn(viewModelScope)
    }

    fun searchUser(query: String) {
        currentQuery.value = query
    }

    companion object {
        const val DEFAULT_QUERY = "kutu"
    }

}