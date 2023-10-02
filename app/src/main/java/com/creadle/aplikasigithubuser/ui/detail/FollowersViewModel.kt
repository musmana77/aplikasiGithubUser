package com.creadle.aplikasigithubuser.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.creadle.aplikasigithubuser.data.response.User
import com.creadle.aplikasigithubuser.data.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    val listFollowers = MutableLiveData<ArrayList<User>>()

    fun setListFollowers(username: String) {
        RetrofitClient.apiInstance
            .getFollowers(username)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        listFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.e("FollowersViewModel", "onFailure: ${t.message}")
                }

            })
    }

    fun getListFollowers(): LiveData<ArrayList<User>> {
        return listFollowers
    }
}