package com.paperplay.daggerpractice.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.paperplay.daggerpractice.data.model.table.PostsTable

/**
 * Created by Ahmed Yusuf on 19/05/20.
 */
@Database(entities = arrayOf(PostsTable::class), version = 1)
abstract class Database: RoomDatabase() {
    abstract fun postDao() : PostDao
}