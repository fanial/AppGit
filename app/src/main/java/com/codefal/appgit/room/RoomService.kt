package com.codefal.appgit.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.codefal.appgit.model.ItemsUsers

@Dao
interface RoomService {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFav(favorite: RoomEntity)

    @Update
    fun updateFav(favorite: RoomEntity)

    @Delete
    fun deleteFav(favorite: RoomEntity)

    @Query("SELECT * FROM RoomEntity ORDER BY login ASC")
    fun getAllFav() : LiveData<List<ItemsUsers>>
}