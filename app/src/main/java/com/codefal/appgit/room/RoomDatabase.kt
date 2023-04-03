package com.codefal.appgit.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RoomEntity::class], version = 1)
abstract class RoomDatabase : RoomDatabase() {

    abstract fun roomDao() : RoomService

}