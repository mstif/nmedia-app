package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
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
        binding.fab.setOnClickListener {
            viewModel.onAddClicked()
        }



//        viewModel.currentPost.observe(this) { currenPost ->
//            binding.content.setText(currenPost?.content)
//            if (currenPost?.content != null && !currenPost.content.isBlank()) {
//                binding.group.visibility = View.VISIBLE
//                binding.shadowContent.setText(currenPost.content)
//
//                binding.content.requestFocus()
//
//
//            }
//        }



        viewModel.sharePostContentModel.observe(this){postContent->
            val intent = Intent().apply {
                action  = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT,postContent)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent,getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        viewModel.playVideoFromPost.observe(this){videoUrl->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))

            val videoPlayIntent = Intent.createChooser(intent,getString(R.string.chooser_player))
            if (videoPlayIntent.resolveActivity(packageManager) != null) {
                startActivity(videoPlayIntent)
            }
        }

        val postContentActivityResultContract = PostContentActivity.ResultContract()//комментарим потому что это сделали object
       val postContentActivityLouncher = registerForActivityResult(postContentActivityResultContract){postContent->
           if(postContent==null){
               viewModel.currentPost.value = null
               return@registerForActivityResult
           }
           viewModel.onSaveButtonClicked(postContent)
       }
        viewModel.navigateToPostScreenEvent.observe(this){

            postContentActivityLouncher.launch(it)


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



