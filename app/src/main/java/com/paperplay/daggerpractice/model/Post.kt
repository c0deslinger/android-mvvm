package com.paperplay.daggerpractice.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Ahmed Yusuf on 10/05/20.
 */
data class Post (
    @SerializedName("userId")
    @Expose
    var userId: Int,

    @SerializedName("id")
    @Expose
    var id: Int = 0,

    @SerializedName("title")
    @Expose
    var title: String?=null,

    @SerializedName("body")
    @Expose
    var body: String?=null,

    var comment: List<Comment> ? =null
)