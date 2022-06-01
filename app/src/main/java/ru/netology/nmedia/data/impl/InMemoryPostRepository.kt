package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {
    override val data = MutableLiveData(Post.demoDataPost())

    private val posts get() = checkNotNull(data.value) { "Data value should not be null" }

    override fun like(id: Long) {

        data.value = posts.map {
            if (it.id == id) it.copy(
                liked = !it.liked,
                likeCount = it.likeCount + if (it.liked) -1 else 1
            ) else it
        }


    }

    override fun share(id: Long) {

        data.value = posts.map { if (it.id == id) it.copy(shareCount = it.shareCount + 1) else it }

    }

}