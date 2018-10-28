package com.renovavision.koincoroutines.presentation.post

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.view.View
import com.renovavision.koincoroutines.R
import com.renovavision.koincoroutines.domain.model.Post
import com.renovavision.koincoroutines.domain.usecase.GetPostsUseCase

class PostListViewModel(private val getPostsUseCase: GetPostsUseCase) : ViewModel() {

    val postListAdapter: PostListAdapter = PostListAdapter()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPosts() }

    init {
        loadPosts()
    }

    override fun onCleared() {
        super.onCleared()
        getPostsUseCase.unsubscribe()
    }

    private fun loadPosts() {
        onRetrievePostListStart()
        getPostsUseCase.execute({
            onRetrievePostListFinish()
            onRetrievePostListSuccess(it)
        }, {
            onRetrievePostListFinish()
            onRetrievePostListError()
        })
    }

    private fun onRetrievePostListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrievePostListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrievePostListSuccess(postList: List<Post>) {
        postListAdapter.updatePostList(postList)
    }

    private fun onRetrievePostListError() {
        errorMessage.value = R.string.post_error
    }
}