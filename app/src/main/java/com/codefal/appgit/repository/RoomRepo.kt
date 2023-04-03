package com.codefal.appgit.repository

import androidx.lifecycle.LiveData
import com.codefal.appgit.model.ItemsUsers
import com.codefal.appgit.room.RoomEntity
import com.codefal.appgit.room.RoomService
import javax.inject.Inject

class RoomRepo @Inject constructor(private val mRoomDao : RoomService) {
    fun getAllFav(): LiveData<List<ItemsUsers>> = mRoomDao.getAllFav()

    fun insert(favorite: RoomEntity){
        mRoomDao.insertFav(favorite)
    }

    fun delete(favorite: RoomEntity){
        mRoomDao.deleteFav(favorite)
    }

    fun update(favorite: RoomEntity){
        mRoomDao.updateFav(favorite)
    }
}