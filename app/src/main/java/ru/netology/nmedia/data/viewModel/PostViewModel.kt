package ru.netology.nmedia.data.viewModel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository

class PostViewModel:ViewModel() {
    private val repository:PostRepository = InMemoryPostRepository()
    val dataViewModel by repository::data
    fun onLikeClicked(post: Post) = repository.like(post.id)
    fun onShareClicked(post:Post) = repository.share(post.id)
}