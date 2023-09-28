package com.creadle.aplikasigithubuser.data.retrofit


import com.creadle.aplikasigithubuser.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface Api {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>
}