package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.db.toEntity
import ru.netology.nmedia.db.toModel
import x.y.z.SingleLiveEvent

class SqLiteRepository(private val dao: PostDao) : PostRepository {

    override val data = dao.getAll().map { entities ->
        entities.map { it.toModel() }
    }


    override val sharePostContent = SingleLiveEvent<String>()
    override val currentPost = MutableLiveData<Post?>(null)


    override fun like(id: Long) {
        dao.likeById(id)

    }

    override fun share(id: Long) {
        dao.shareById(id)
        sharePostContent.value = data.value?.find { it.id == id }?.content

    }

    override fun delete(id: Long) {
        dao.removeById(id)
    }

    override fun save(post: Post) {

        dao.save(post.toEntity())

    }

    // object SingletonData
    //  { val currentPost = MutableLiveData<Post?>(null)
    // val data = MutableLiveData<List<Post>>(null)
    // }

    //private fun setCurrentPost(id: Long) {
    //    currentPost.value = data.value?.find { it.id == id }
    // }

    override fun getPostById(id: Long): Post {
        val p = dao.getPostById(id).toModel()
        return p
    }

}