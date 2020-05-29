package com.paperplay.daggerpractice.data.model.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Created by Ahmed Yusuf on 19/05/20.
 */
@Entity(
    tableName = "Posts"
)
data class PostsTable (
    @PrimaryKey
    val id: Int = 0,
    var title: String?=null,
    var body: String?=null,
    var comments_count: Int?=null
): Serializable