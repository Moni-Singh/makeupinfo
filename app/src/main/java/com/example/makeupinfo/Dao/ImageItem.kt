package com.example.makeupinfo.Dao

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "imageItems")
data class ImageItem(
   @PrimaryKey(autoGenerate = true)
    val id: Int,
    val liked: Int,
    val Bookmarks : Boolean
)
