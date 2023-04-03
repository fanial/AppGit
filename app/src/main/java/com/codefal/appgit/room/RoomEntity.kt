package com.codefal.appgit.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class RoomEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "login")
    var login: String = "",

    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String = "",

) : Parcelable