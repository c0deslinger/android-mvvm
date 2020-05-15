package com.paperplay.daggerpractice.network.state

/**
 * class ini merupakan standart wrapper dari google untuk wrapping return dari REST API agar mudah diproses
 */
class Resource<T>(status: Status, data: T?, message: String?) {
    val status: Status
    val data: T?
    val message: String?

    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(msg: String, data: T): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                msg
            )
        }

        fun <T> loading(data: T): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null
            )
        }
    }

    init {
        this.status = status
        this.data = data
        this.message = message
    }
}