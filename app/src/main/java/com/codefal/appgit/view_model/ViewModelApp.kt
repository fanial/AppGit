package com.codefal.appgit.view_model

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codefal.appgit.model.ItemsUsers
import com.codefal.appgit.model.ResponseSearch
import com.codefal.appgit.model.ResponseUsers
import com.codefal.appgit.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelApp @Inject constructor(private val apiService: ApiService) :ViewModel() {
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

    private val _message = MutableLiveData<String>()
    val isMessege : LiveData<String> = _message

    fun getSearch(username : String){
        _loading.value = true
        val client = apiService.search(username)
        client.enqueue(object : Callback<ResponseSearch>{
            override fun onResponse(
                call: Call<ResponseSearch>,
                response: Response<ResponseSearch>
            ) {
                _loading.value = false
                if (response.isSuccessful){
                    _search.postValue(response.body()?.items)
                    _message.postValue(response.body()?.totalCount.toString())
                    Log.e(TAG, "onResponse: Success Load Data, ${response.body()}")
                }else{
                    _message.postValue(response.message())
                    Log.e(TAG, "onResponse: Failed ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseSearch>, t: Throwable) {
                _loading.value = false
                _message.value = t.message
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getUsers(username : String){
        _loading.value = true
        val client = apiService.user(username)
        client.enqueue(object : Callback<ResponseUsers>{
            override fun onResponse(call: Call<ResponseUsers>, response: Response<ResponseUsers>) {
                _loading.value = false
                if (response.isSuccessful){
                    _user.value = response.body()
                    Log.e(TAG, "onResponse: Success Load Data, ${response.body()}")
                }else{
                    _message.postValue(response.message())
                    Log.e(TAG, "onResponse: Failed ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseUsers>, t: Throwable) {
                _loading.value = false
                _message.value = t.message
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getFollowers(username: String){
        _loading.value = true
        val client = apiService.followers(username)
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
                    _message.postValue(response.message())
                    Log.e(TAG, "onResponse: Failed ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsUsers>>, t: Throwable) {
                _loading.value = false
                _message.value = t.message
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getFollowing(username: String){
        _loading.value = true
        val client = apiService.following(username)
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
                    _message.postValue(response.message())
                    Log.e(TAG, "onResponse: Failed ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsUsers>>, t: Throwable) {
                _loading.value = false
                _message.value = t.message
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

}