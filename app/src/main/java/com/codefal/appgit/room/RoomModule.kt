package com.codefal.appgit.room

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun getInstance(@ApplicationContext app : Context) = Room.databaseBuilder(
        app, RoomDatabase::class.java, "GitDB"
    ).build()

    @Singleton
    @Provides
    fun getDao(db : RoomDatabase) = db.roomDao()
}