package com.codefal.appgit.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.codefal.appgit.model.ItemsUsers
import com.codefal.appgit.model.ResponseUsers

@Dao
interface RoomService {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFav(favorite: RoomEntity)

    @Delete
    fun deleteFav(favorite: RoomEntity)

    @Query("SELECT * FROM RoomEntity ORDER BY login ASC")
    fun getAllFav() : LiveData<List<ItemsUsers>>

    @Query("SELECT * FROM RoomEntity WHERE login = :username")
    fun getFavByUser(username : String) : LiveData<ResponseUsers>
}