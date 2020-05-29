package com.paperplay.daggerpractice.data.mapper

/**
 * Created by Ahmed Yusuf on 19/05/20.
 */
interface EntityMapper<E, D>{
    fun mapToEntity(domain: D): E
}