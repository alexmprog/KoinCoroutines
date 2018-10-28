package com.renovavision.koincoroutines.domain.repository

import com.renovavision.koincoroutines.domain.model.Post
import java.io.IOException

interface PostRepository {

    @Throws(IOException::class)
    suspend fun getPosts(): List<Post>
}
