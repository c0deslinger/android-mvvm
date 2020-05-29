package com.paperplay.daggerpractice.data.mapper

import com.paperplay.daggerpractice.data.model.table.PostsTable
import com.paperplay.daggerpractice.data.model.response.PostResponse
import javax.inject.Inject

/**
 * Created by Ahmed Yusuf on 19/05/20.
 */
open class PostMapper @Inject constructor() : EntityMapper<PostsTable, PostResponse> {
    override fun mapToEntity(domain: PostResponse): PostsTable {
        return PostsTable(
            id = domain.id,
            body = domain.body,
            title = domain.title
        )
    }
}