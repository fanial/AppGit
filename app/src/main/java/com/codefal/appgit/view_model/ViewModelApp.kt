package com.codefal.appgit.view_model

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codefal.appgit.model.ItemsUsers
import com.codefal.appgit.model.ResponseSearch
import com.codefal.appgit.model.ResponseUsers
import com.codefal.appgit.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelApp:ViewModel() {
    private val _search = MutableLiveData<List<ItemsUsers>?>()
    fun liveSearch() : MutableLiveData<List<ItemsUsers>?> = _search

    private val _user = MutableLiveData<ResponseUsers>()
    val liveUser : LiveData<ResponseUsers> = _user

    private val _followers = MutableLiveData<List<ItemsUsers>?>()
    val liveFollowers : LiveData<List<ItemsUsers>?> = _followers

    private val _following = MutableLiveData<List<ItemsUsers>?>()
    val liveFollowing : LiveData<List<ItemsUsers>?> = _following

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
        client.enqueue(object : Callback<List<ItemsUsers>>{
            override fun onResponse(
                call: Call<List<ItemsUsers>>,
                response: Response<List<ItemsUsers>>
            ) {
                _loading.value = false
                if (response.isSuccessful){
                    _followers.postValue(response.body())
                    Log.e(TAG, "onResponse: Success Load Data, ${response.body()}")
                }else{
                    Log.e(TAG, "onResponse: Failed ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsUsers>>, t: Throwable) {
                _loading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getFollowing(username: String){
        _loading.value = true
        val client = ApiConfig.instance.following(username)
        client.enqueue(object : Callback<List<ItemsUsers>>{
            override fun onResponse(
                call: Call<List<ItemsUsers>>,
                response: Response<List<ItemsUsers>>
            ) {
                _loading.value = false
                if (response.isSuccessful){
                    _following.value = response.body()
                    Log.e(TAG, "onResponse: Success Load Data, ${response.body()}")
                }else{
                    Log.e(TAG, "onResponse: Failed ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsUsers>>, t: Throwable) {
                _loading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

}