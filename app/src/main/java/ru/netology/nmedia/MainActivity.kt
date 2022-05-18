package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class MainActivity : AppCompatActivity() {
    var post = Post()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            post = Post.demoDataPost()
            contentPost.text = post.content
            author.text = post.author
            published.text = post.published
            likeCount.text = getStringOfCount(post.likeCount)
            shareCount.text = getStringOfCount(post.shareCount)
            eyeCount.text = getStringOfCount(post.seenCount)
            fun setResorceLike(post: Post) =
                imageButtonFavorit.setImageResource(
                    if (post.liked) R.drawable.ic_liked_24 else
                        R.drawable.ic_baseline_favorite_border_24
                )

            setResorceLike(post)
            imageButtonFavorit.setOnClickListener {
                val liked = !post.liked
                val likeCnt = post.likeCount + if (liked) 1 else -1
                post = post.copy(liked = liked, likeCount = likeCnt)
                setResorceLike(post)
                likeCount.text = getStringOfCount(post.likeCount)


            }
            imageButtonShare.setOnClickListener {
                post = post.copy(shareCount = post.shareCount + 1)
                shareCount.text = getStringOfCount(post.shareCount)
            }


        }
    }

    fun getStringOfCount(count: Int): String {

        return when (count) {
            in 0..999 -> count.toString()
            in 1000..10000 -> {
                val firstDigi = (count / 1000)
                val secondDigi = ((count % 1000) / 100)
                var secondPart = ".${secondDigi}K"

                if (secondDigi == 0) secondPart = "K"
                "$firstDigi$secondPart"
            }
            in 10001..999999 -> {
                "${(count / 1000)}K"
            }
            else -> {
                val firstDigi = (count / 1_000_000)
                val secondDigi = ((count % 1_000_000) / 100_000)
                var secondPart = ".${secondDigi}M"

                if (secondDigi == 0) secondPart = "M"
                "$firstDigi$secondPart"

            }
        }
    }

}