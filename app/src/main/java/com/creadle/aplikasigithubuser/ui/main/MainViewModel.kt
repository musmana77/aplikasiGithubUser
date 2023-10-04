package com.creadle.aplikasigithubuser.ui.main


import android.app.Application
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.creadle.aplikasigithubuser.R
import com.creadle.aplikasigithubuser.data.response.User
import com.creadle.aplikasigithubuser.data.response.UserResponse
import com.creadle.aplikasigithubuser.data.retrofit.ApiConfig
import com.creadle.aplikasigithubuser.ui.mode.UIModePreference
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MainViewModel (application: Application): AndroidViewModel(application) {

    val listUsers = MutableLiveData<ArrayList<User>>()

    private val uiDataStore = UIModePreference.getInstance(application)
    val getUIMode = uiDataStore.uiMode

    private fun saveToDataStore(isNightMode: Boolean){
        viewModelScope.launch (IO){
            uiDataStore.saveToDataStore(isNightMode)
        }
    }

    fun setSearchUsers(query: String){

        ApiConfig.apiClientInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful){
                        listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.e("MainViewModel", "onFailure: ${t.message}")
                }

            })
    }
    fun getSearchUsers(): LiveData<ArrayList<User>>{
        return listUsers
    }

    fun setUIMode(item: MenuItem, isChecked: Boolean) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            saveToDataStore(true)
            item.setIcon(R.drawable.ic_day_mode)

        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            saveToDataStore(false)
            item.setIcon(R.drawable.ic_night_mode)

        }
    }
}
