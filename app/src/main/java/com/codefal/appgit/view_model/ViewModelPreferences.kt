package com.codefal.appgit.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.codefal.appgit.repository.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelPreferences @Inject constructor(private val dataStore: DataStoreManager): ViewModel(){

    fun liveThemeSettings() : LiveData<Boolean>{
        return dataStore.getThemeSetting().asLiveData()
    }

    fun liveSaveThemeSettings(isDarkModeActive: Boolean){
        viewModelScope.launch {
            dataStore.saveTheme(isDarkModeActive)
        }
    }

}