package com.creadle.aplikasigithubuser.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.creadle.aplikasigithubuser.data.response.User
import com.creadle.aplikasigithubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<User>>()

    fun setListFollowing(username: String) {
        ApiConfig.apiClientInstance
            .getFollowing(username)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.e("FollowingViewModel", "onFailure: ${t.message}")
                }

            })
    }

    fun getListFollowing(): LiveData<ArrayList<User>> {
        return listFollowing
    }
}