package ru.netology.nmedia.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="posts")
class PostEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long ,
    val content: String ,
    val published: String ,
    val author: String ,
    @ColumnInfo(name = "liked")
    val liked: Boolean ,
    val likeCount: Int ,
    val shareCount: Int ,
    val seenCount: Int ,
    val video: String
)