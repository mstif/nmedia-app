package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.data.viewModel.PostViewModel
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.utils.hideKeyboard

class MainActivity : AppCompatActivity() {

    val viewModel: PostViewModel by viewModels<PostViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = PostsAdapter(viewModel)
        binding.container.adapter = adapter
        viewModel.dataViewModel.observe(this) { posts ->
            adapter.submitList(posts)
        }
        binding.save.setOnClickListener {
            with(binding.content) {
                val content = text.toString()
                viewModel.onSaveButtonClicked(content)
                clearFocus()
                hideKeyboard()
            }
        }
        viewModel.currentPost.observe(this){currenPost->
            binding.content.setText(currenPost?.content)
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



