package com.kucherenko.ronis.compose_app.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kucherenko.ronis.compose_app.data.models.UserProfile
import com.kucherenko.ronis.compose_app.data.repository.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FriendsViewModel(private val repository: UsersRepository = UsersRepository()) : ViewModel() {

    private val _usersFLow =
        MutableStateFlow<List<UserProfile>>(emptyList())
    val usersFLow = _usersFLow.asStateFlow()

    private val _selectedUserFlow =
        MutableStateFlow<UserProfile?>(null)
    val selectedUserFlow = _selectedUserFlow.asStateFlow()

    //mocked loading from server
    suspend fun loadFriends() {
        val friends = repository.loadUsers()
        _usersFLow.emit(friends)
    }

    fun selectUser(userProfile: UserProfile) {
        viewModelScope.launch {
            _selectedUserFlow.emit(userProfile)
        }
    }
}