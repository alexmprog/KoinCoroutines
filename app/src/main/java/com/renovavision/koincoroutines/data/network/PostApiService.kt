package com.renovavision.koincoroutines.data.network

import com.renovavision.koincoroutines.domain.model.Post
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET

interface PostApiService {

    @GET("/posts")
    fun getPosts(): Deferred<List<Post>>
}