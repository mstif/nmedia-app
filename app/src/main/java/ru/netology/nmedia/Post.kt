package ru.netology.nmedia

data class Post(
    val id: Int = 0,
    val content: String = "",
    val published: String = "",
    val author: String = "",
    val liked: Boolean=false,
    val likeCount:Int = 0,
    val shareCount:Int = 0,
    val seenCount:Int =0
) {
    companion object {
        fun demoDataPost():Post {
            return Post(id=1,
                content ="Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                published = "13.05.2022",
                author = "Всемирная ассоциация любителей всего",
                liked = false,
                likeCount = 311999689,
                shareCount = 5,
                seenCount =  89
                )

        }
    }
}
