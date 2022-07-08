package ru.netology.nmedia.db

import android.database.Cursor
import ru.netology.nmedia.Post

fun Cursor.toPost() = Post(
    id = getLong(getColumnIndexOrThrow(PostTable.Column.ID.columnName)),
    content = getString(getColumnIndexOrThrow(PostTable.Column.CONTENT.columnName)),
    author = getString(getColumnIndexOrThrow(PostTable.Column.AUTHOR.columnName)),
    video = getString(getColumnIndexOrThrow(PostTable.Column.VIDEO.columnName)),
    seenCount = getInt(getColumnIndexOrThrow(PostTable.Column.SEENCOUNT.columnName)),
    shareCount = getInt(getColumnIndexOrThrow(PostTable.Column.SHARECONT.columnName)),
    published = getString(getColumnIndexOrThrow(PostTable.Column.PUBLISHED.columnName)),
    likeCount = getInt(getColumnIndexOrThrow(PostTable.Column.LIKECOUNT.columnName)),
    liked = getInt(getColumnIndexOrThrow(PostTable.Column.LIKED.columnName)) != 0
)