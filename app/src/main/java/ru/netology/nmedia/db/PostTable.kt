package ru.netology.nmedia.db

object PostTable{
    const val NAME = "posts"
    val ddl:String = """
    CREATE TABLE $NAME (
        ${Column.ID.columnName} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${Column.CONTENT.columnName} TEXT NOT NULL,
        ${Column.PUBLISHED.columnName} TEXT NOT NULL,
        ${Column.AUTHOR.columnName} TEXT NOT NULL,
        ${Column.LIKED.columnName} BOOLEAN NOT NULL DEFAULT 0,
        ${Column.SHARECONT.columnName} INTEGER NOT NULL DEFAULT 0,
        ${Column.LIKECOUNT.columnName} INTEGER NOT NULL DEFAULT 0,
        ${Column.SEENCOUNT.columnName} INTEGER NOT NULL DEFAULT 0,
        ${Column.VIDEO.columnName} TEXT NOT NULL DEFAULT ''
    );
    """.trimIndent()

       val ALL_COLUMNS_NAMES = Column.values().map{it.columnName}.toTypedArray()

    enum class Column(val columnName:String){
        ID ("id") ,
        CONTENT("content"),
        PUBLISHED("published"),
        AUTHOR("author"),
        LIKED("liked"),
        LIKECOUNT("likecount"),
        SHARECONT("sharecount"),
        SEENCOUNT("seencount") ,
        VIDEO("video")

    }

}