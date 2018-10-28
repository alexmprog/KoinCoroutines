package com.renovavision.koincoroutines.domain.usecase

import com.renovavision.koincoroutines.domain.model.Post
import com.renovavision.koincoroutines.domain.repository.PostRepository

class GetPostsUseCase(private val postRepository: PostRepository) : UseCase<List<Post>>() {

    override suspend fun executeOnBackground(): List<Post> = postRepository.getPosts()

}