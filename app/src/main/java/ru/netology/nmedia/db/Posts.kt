package ru.netology.nmedia.db

import android.database.Cursor
import ru.netology.nmedia.Post



fun PostEntity.toModel() = Post(
    id = id,
    content = content,
    author = author,
    video = video,
    seenCount = seenCount,
    shareCount = shareCount,
    published = published,
    likeCount = likeCount,
    liked = liked
)


fun Post.toEntity() = PostEntity(
    id = id,
    content = content,
    author = author,
    video = video,
    seenCount = seenCount,
    shareCount = shareCount,
    published = published,
    likeCount = likeCount,
    liked = liked
)