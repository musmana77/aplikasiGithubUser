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