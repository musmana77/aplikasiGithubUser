package com.creadle.aplikasigithubuser.data.retrofit


import com.creadle.aplikasigithubuser.data.response.DetailUserResponse
import com.creadle.aplikasigithubuser.data.response.User
import com.creadle.aplikasigithubuser.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface Api {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>

}