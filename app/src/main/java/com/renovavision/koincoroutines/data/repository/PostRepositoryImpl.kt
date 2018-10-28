package com.renovavision.koincoroutines.data.repository

import com.renovavision.koincoroutines.data.database.PostDao
import com.renovavision.koincoroutines.domain.repository.PostRepository
import com.renovavision.koincoroutines.domain.model.Post
import com.renovavision.koincoroutines.data.network.PostApiService

class PostRepositoryImpl(private val postApiService: PostApiService, private val postDao: PostDao) : PostRepository {

    override suspend fun getPosts(): List<Post> {
        val all = postDao.all
        if (all.isEmpty()) {
            val result = postApiService.getPosts().await()
            postDao.insertAll(*result.toTypedArray())
            return result
        } else {
            return all
        }
    }
}