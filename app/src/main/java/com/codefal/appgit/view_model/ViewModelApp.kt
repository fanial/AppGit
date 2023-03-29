package com.codefal.appgit.view_model

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codefal.appgit.model.*
import com.codefal.appgit.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelApp:ViewModel() {
    private val _search = MutableLiveData<List<ItemsSearch>?>()
    fun liveSearch() : MutableLiveData<List<ItemsSearch>?> = _search

    private val _user = MutableLiveData<ResponseUsers>()
    val liveUser : LiveData<ResponseUsers> = _user

    private val _followers = MutableLiveData<List<ResponseFollowItem>?>()
    val liveFollowers : LiveData<List<ResponseFollowItem>?> = _followers

    private val _following = MutableLiveData<List<ResponseFollowItem>?>()
    val liveFollowing : LiveData<List<ResponseFollowItem>?> = _following

    private val _loading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _loading

    fun getSearch(username : String){
        _loading.value = true
        val client = ApiConfig.instance.search(username)
        client.enqueue(object : Callback<ResponseSearch>{
            override fun onResponse(
                call: Call<ResponseSearch>,
                response: Response<ResponseSearch>
            ) {
                _loading.value = false
                if (response.isSuccessful){
                    _search.postValue(response.body()?.items)
                    Log.e(TAG, "onResponse: Success Load Data, ${response.body()}")
                }else{
                    Log.e(TAG, "onResponse: Failed ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseSearch>, t: Throwable) {
                _loading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getUsers(username : String){
        _loading.value = true
        val client = ApiConfig.instance.user(username)
        client.enqueue(object : Callback<ResponseUsers>{
            override fun onResponse(call: Call<ResponseUsers>, response: Response<ResponseUsers>) {
                _loading.value = false
                if (response.isSuccessful){
                    _user.value = response.body()
                    Log.e(TAG, "onResponse: Success Load Data, ${response.body()}")
                }else{
                    Log.e(TAG, "onResponse: Failed ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseUsers>, t: Throwable) {
                _loading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getFollowers(username: String){
        _loading.value = true
        val client = ApiConfig.instance.followers(username)
        client.enqueue(object : Callback<List<ResponseFollowItem>>{
            override fun onResponse(
                call: Call<List<ResponseFollowItem>>,
                response: Response<List<ResponseFollowItem>>
            ) {
                _loading.value = false
                if (response.isSuccessful){
                    _followers.postValue(response.body())
                    Log.e(TAG, "onResponse: Success Load Data, ${response.body()}")
                }else{
                    Log.e(TAG, "onResponse: Failed ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ResponseFollowItem>>, t: Throwable) {
                _loading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getFollowing(username: String){
        _loading.value = true
        val client = ApiConfig.instance.following(username)
        client.enqueue(object : Callback<List<ResponseFollowItem>>{
            override fun onResponse(
                call: Call<List<ResponseFollowItem>>,
                response: Response<List<ResponseFollowItem>>
            ) {
                _loading.value = false
                if (response.isSuccessful){
                    _following.value = response.body()
                    Log.e(TAG, "onResponse: Success Load Data, ${response.body()}")
                }else{
                    Log.e(TAG, "onResponse: Failed ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ResponseFollowItem>>, t: Throwable) {
                _loading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

}