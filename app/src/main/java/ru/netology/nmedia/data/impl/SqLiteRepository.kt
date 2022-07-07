package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.db.PostDao
import x.y.z.SingleLiveEvent

class SqLiteRepository(private val dao: PostDao) : PostRepository {
    val singltonData by lazy { SingltonData }

    //override val data = MutableLiveData(dao.getAll())
    override val data: MutableLiveData<List<Post>>
        get() = singltonData.data
    override val sharePostContent = SingleLiveEvent<String>()
    override val currentPost: MutableLiveData<Post?>
        get() = singltonData.currentPost


    private val posts get() = checkNotNull(data.value) { "Data value should not be null" }

    init {
        data.value = dao.getAll()
    }

    override fun like(id: Long) {
        dao.likeById(id)
        data.value = posts.map {
            if (it.id == id) {
                val post = it.copy(
                    liked = !it.liked,
                    likeCount = it.likeCount + if (it.liked) -1 else 1
                )
                currentPost.value = post
                post
            } else it
        }


    }

    override fun share(id: Long) {
        dao.shareById(id)
        data.value = posts.map {
            if (it.id == id) it.copy(shareCount = it.shareCount + 1)
                .apply { sharePostContent.value = it.content } else it
        }
        setCurrentPost(id)


    }

    override fun delete(id: Long) {
        dao.removeById(id)
        data.value = posts.filter { it.id != id }
        setCurrentPost(id)
    }

    override fun save(post: Post) {

        val postDao = dao.save(post)
        if (post.id == PostRepository.NEW_POST_ID) insert(postDao) else update(postDao)
        setCurrentPost(post.id)
    }

    private fun update(post: Post) {
        data.value = posts.map { if (it.id == post.id) post else it }
    }

    private fun insert(post: Post) {
        data.value =
            listOf(post.copy()) + posts
    }

    object SingltonData {
        val currentPost = MutableLiveData<Post?>(null)
        val data = MutableLiveData<List<Post>>(null)
    }

    fun setCurrentPost(id: Long) {
        currentPost.value = data.value?.find { it.id == id }
    }


}