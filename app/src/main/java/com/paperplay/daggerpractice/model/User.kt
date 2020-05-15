package com.paperplay.daggerpractice.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Ahmed Yusuf on 09/05/20.
 */
data class User (

    @SerializedName("id")
    @Expose
    var id: Int?=null,

    @SerializedName("username")
    @Expose
    var username: String?=null,

    @SerializedName("email")
    @Expose
    var email: String?=null,

    @SerializedName("website")
    @Expose
    var website: String?=null

)