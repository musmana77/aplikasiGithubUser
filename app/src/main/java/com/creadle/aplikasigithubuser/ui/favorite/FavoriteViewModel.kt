package com.creadle.aplikasigithubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.creadle.aplikasigithubuser.data.local.FavoriteUser
import com.creadle.aplikasigithubuser.data.local.FavoriteUserDao
import com.creadle.aplikasigithubuser.data.local.UserDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var  userDao: FavoriteUserDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>? {
        return userDao?.getFavoriteUser()
    }

}