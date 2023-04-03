package com.codefal.appgit.view_model

import androidx.lifecycle.ViewModel
import com.codefal.appgit.repository.RoomRepo
import com.codefal.appgit.room.RoomEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModelRoom @Inject constructor(private val mRoomRepo: RoomRepo): ViewModel() {

    fun insert(favorite : RoomEntity){
        mRoomRepo.insert(favorite)
    }

    fun update(favorite : RoomEntity){
        mRoomRepo.update(favorite)
    }

    fun delete(favorite : RoomEntity){
        mRoomRepo.delete(favorite)
    }
}