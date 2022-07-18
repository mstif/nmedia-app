package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.db.toEntity
import ru.netology.nmedia.db.toModel
import x.y.z.SingleLiveEvent

class SqLiteRepository(private val dao: PostDao) : PostRepository {
   //private val singletonData by lazy { SingletonData }

    //override val data = MutableLiveData(dao.getAll())
    override val data = dao.getAll().map { entities ->
        entities.map { it.toModel() }
    }


    override val sharePostContent = SingleLiveEvent<String>()
    override val currentPost = MutableLiveData<Post?>(null)





    override fun like(id: Long) {
        dao.likeById(id)

       // setCurrentPost(id)


    }

    override fun share(id: Long) {
        dao.shareById(id)
        sharePostContent.value = data.value?.find { it.id==id }?.content
       // setCurrentPost(id)


    }

    override fun delete(id: Long) {
        dao.removeById(id)
        setCurrentPost(id)
    }

    override fun save(post: Post) {

        dao.save(post.toEntity())

        //setCurrentPost(post.id)
    }

   // object SingletonData
  //  { val currentPost = MutableLiveData<Post?>(null)
       // val data = MutableLiveData<List<Post>>(null)
   // }

    private fun setCurrentPost(id: Long) {
        currentPost.value = data.value?.find { it.id == id }
    }


}