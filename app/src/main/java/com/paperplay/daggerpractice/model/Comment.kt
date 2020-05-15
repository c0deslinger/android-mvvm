package com.paperplay.daggerpractice.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Ahmed Yusuf on 14/05/20.
 */
data class Comment (
    @SerializedName("postId")
    @Expose
    var postId: Int? = null,

    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @Expose
    @SerializedName("name")
    var name: String? = null,

    @Expose
    @SerializedName("email")
    var email: String? = null,

    @Expose
    @SerializedName("body")
    var body: String? = null
)