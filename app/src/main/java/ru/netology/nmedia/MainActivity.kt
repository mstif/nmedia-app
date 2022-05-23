package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.data.viewModel.PostViewModel
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val viewModel: PostViewModel by viewModels<PostViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {

            imageButtonFavorit.setOnClickListener {
                viewModel.onLikeClicked()
            }
            imageButtonShare.setOnClickListener {
                viewModel.onShareClicked()
            }
        }

        viewModel.dataViewModel.observe(this) { post ->
            binding.render(post)
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

fun ActivityMainBinding.render(post: Post) {
    imageButtonFavorit.setImageResource(
        if (post.liked) R.drawable.ic_liked_24 else
            R.drawable.ic_baseline_favorite_border_24
    )

    contentPost.text = post.content
    author.text = post.author
    published.text = post.published
    likeCount.text = getStringOfCount(post.likeCount)
    shareCount.text = getStringOfCount(post.shareCount)
    eyeCount.text = getStringOfCount(post.seenCount)
}
