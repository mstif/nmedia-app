package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {
    override val data = MutableLiveData(Post.demoDataPost())


    override fun like() {
        val currentPost = checkNotNull(data.value) { "Data value should not be null" }
        val liked = !currentPost.liked
        val likeCnt = currentPost.likeCount + if (liked) 1 else -1
        data.value = currentPost.copy(liked = liked, likeCount = likeCnt)
    }

    override fun share() {

        val currentPost = checkNotNull(data.value) { "Data value should not be null" }
        data.value = currentPost.copy(shareCount = currentPost.shareCount + 1)

    }

}