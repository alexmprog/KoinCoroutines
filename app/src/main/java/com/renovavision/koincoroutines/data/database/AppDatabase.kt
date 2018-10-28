package com.renovavision.koincoroutines.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.renovavision.koincoroutines.domain.model.Post

@Database(entities = [Post::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}