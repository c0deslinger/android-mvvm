package com.paperplay.daggerpractice.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.paperplay.daggerpractice.data.model.table.PostsTable
import io.reactivex.Single

/**
 * Created by Ahmed Yusuf on 19/05/20.
 */
@Dao
interface PostDao {
    @Query("SELECT * from Posts")
    fun getPost(): Single<MutableList<PostsTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(postsTable: PostsTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPosts(list: List<PostsTable>)

}