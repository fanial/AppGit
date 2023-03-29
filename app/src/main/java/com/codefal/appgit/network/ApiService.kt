package com.codefal.appgit.network

import com.codefal.appgit.model.ResponseFollowItem
import com.codefal.appgit.model.ResponseSearch
import com.codefal.appgit.model.ResponseUsers
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users?")
    fun search (
        @Query("q") query : String
    ) : Call<ResponseSearch>

    @GET("users/{username}")
    fun user (
        @Path("username") username: String
    ) : Call<ResponseUsers>

    @GET("users/{username}/followers")
    fun followers (
        @Path("username") username: String
    ) : Call<List<ResponseFollowItem>>

    @GET("users/{username}/following")
    fun following (
        @Path("username") username: String
    ) : Call<List<ResponseFollowItem>>
}