package ru.netology.nmedia.data.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository

class PostViewModel : ViewModel(), PostInteractionListener {
    private val repository: PostRepository = InMemoryPostRepository()
    val dataViewModel by repository::data
    val currentPost = MutableLiveData<Post?>( null)
    override fun onLikeClicked(post: Post) = repository.like(post.id)
    override fun onShareClicked(post: Post) = repository.share(post.id)
    override fun onDeleteClicked(post: Post) = repository.delete(post.id)
    override fun onEditClicked(post: Post) {
        currentPost.value = post
    }



    fun onSaveButtonClicked(content: String) {
        if(content.isBlank())return

        val newPost = currentPost.value?.copy(content = content)?:
        Post(
            id = PostRepository.NEW_POST_ID,
            content = content,
            published = "Today", author = "Me"
        )
        repository.save(newPost)
        currentPost.value = null
    }
}