package ru.netology.nmedia.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.Post

class PostDaoImpl(private val db: SQLiteDatabase) : PostDao {
    override fun getAll(): List<Post> =
        db.query(
            PostTable.NAME,
            PostTable.ALL_COLUMNS_NAMES,
            null, null, null, null,
            "${PostTable.Column.ID.columnName} DESC "
        ).use { cursor ->

            List(cursor.count) {
                cursor.moveToNext()
                cursor.toPost()
            }
        }


    override fun save(post: Post): Post {
        val values = ContentValues().apply {
            put(PostTable.Column.AUTHOR.columnName, post.author)
            //  put(PostTable.Column.LIKECOUNT.columnName,post.likeCount)
            put(PostTable.Column.CONTENT.columnName, post.content)
            put(PostTable.Column.SHARECONT.columnName, post.shareCount)
            put(PostTable.Column.SEENCOUNT.columnName, post.seenCount)
            // put(PostTable.Column.LIKED.columnName,post.liked)
            put(PostTable.Column.PUBLISHED.columnName, post.published)
            put(PostTable.Column.VIDEO.columnName, post.video)
            put(PostTable.Column.SEENCOUNT.columnName, post.seenCount)
        }
        val id = if (post.id != 0L) {
            db.update(
                PostTable.NAME,
                values,
                "${PostTable.Column.ID.columnName} = ? ",
                arrayOf(post.id.toString())
            )
            post.id
        } else {
            db.insert(PostTable.NAME, null, values)
        }
        return db.query(
            PostTable.NAME,
            PostTable.ALL_COLUMNS_NAMES,
            "${PostTable.Column.ID.columnName} = ?",
            arrayOf(id.toString()), null, null,
            null
        ).use { cursor ->
            cursor.moveToNext()
            cursor.toPost()
        }
    }

    override fun likeById(id: Long) {
        db.execSQL(
            """
                UPDATE ${PostTable.NAME} SET
                likecount = likecount + CASE WHEN liked THEN - 1 ELSE  1 END,
                liked = CASE WHEN liked THEN 0 ELSE  1 END
                WHERE id = ?;
                
            """.trimIndent(),
            arrayOf(id)
        )
    }

    override fun removeById(id: Long) {
        db.delete(
            PostTable.NAME,
            "${PostTable.Column.ID.columnName} = ?",
            arrayOf(id.toString())
        )
    }

    override fun shareById(id: Long) {
        db.execSQL(
            """
                UPDATE ${PostTable.NAME} SET
                ${PostTable.Column.SHARECONT.columnName} = ${PostTable.Column.SHARECONT.columnName} +  1 
                WHERE id = ?;
                
            """.trimIndent(),
            arrayOf(id)
        )
    }
}