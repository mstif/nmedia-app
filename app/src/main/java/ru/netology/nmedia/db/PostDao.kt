package ru.netology.nmedia.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.netology.nmedia.data.PostRepository

@Dao
interface PostDao {
    @Query("SELECT * FROM posts ORDER BY id DESC")
    fun getAll():LiveData<List<PostEntity>>

    @Insert
    fun insert(post: PostEntity)

    @Query("UPDATE posts SET CONTENT = :content, VIDEO = :video WHERE id = :id")
    fun updateContentById(id:Long,content:String,video:String)

    fun save(post:PostEntity) =
        if( post.id == PostRepository.NEW_POST_ID) insert(post) else updateContentById(post.id,post.content,post.video)

    @Query("""
        UPDATE posts SET likeCount = likeCount + CASE WHEN liked THEN -1 ELSE 1 END,
        liked = CASE WHEN liked=0 THEN 1 ELSE 0 END
        WHERE id= :id
    """)
    fun likeById(id:Long)

    @Query("DELETE FROM posts WHERE id=:id")
    fun removeById(id:Long)

    @Query("""
        UPDATE posts SET shareCount = shareCount +  1 
        WHERE id= :id
    """)
    fun shareById(id:Long)
}