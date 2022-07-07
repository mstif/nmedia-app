package ru.netology.nmedia.data.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

import ru.netology.nmedia.Post

import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.FilePostRepository
import ru.netology.nmedia.data.impl.SqLiteRepository
import ru.netology.nmedia.db.AppDb
import x.y.z.SingleLiveEvent
import java.text.SimpleDateFormat
import java.util.*

class PostViewModel(application: Application) : AndroidViewModel(application),
    PostInteractionListener {
    //private val repository: PostRepository = FilePostRepository(application)
     private val repository: PostRepository = SqLiteRepository(
        dao = AppDb.getInstance(
        context = application
        ).postDao
    )
    val dataViewModel by repository::data
    val sharePostContentModel by repository::sharePostContent
    val navigateToPostScreenEvent = SingleLiveEvent<String>()
    val navigateToPostSingle = SingleLiveEvent<Post>()
    val playVideoFromPost = SingleLiveEvent<String>()

    // val currentSinglePost by repository::currentSinglePost
    val currentPost by repository::currentPost
    override fun onLikeClicked(post: Post) = repository.like(post.id)
    override fun onShareClicked(post: Post) = repository.share(post.id)
    override fun onDeleteClicked(post: Post) = repository.delete(post.id)
    override fun onEditClicked(post: Post) {
        currentPost.value = post
        navigateToPostScreenEvent.value = post.content

        //navigateToPostScreenEvent.call()
    }

    override fun onPlayVideo(url: String) {
        playVideoFromPost.value = url
    }

    override fun onNavigateClicked(post: Post) {
        navigateToPostSingle.value = post
    }


    fun onSaveButtonClicked(content: String) {
        if (content.isBlank()) return
        val url =  getVideoUrl(content)
        val sdf = SimpleDateFormat("dd.MM.yyyy hh:mm",Locale.getDefault())
        val currentDate = sdf.format(Date())
        val editedPost = currentPost.value?.copy(content = content,video = url) ?: Post(
            id = PostRepository.NEW_POST_ID,
            content = content,
            published = currentDate.toString(), author = "Me",
            video = url
        )

        repository.save(editedPost)
        // currentPost.value = null
    }


    fun onAddClicked() {
        navigateToPostScreenEvent.call()
    }

    private fun  getVideoUrl(content: String): String {
        val startIndex = content.indexOf("http")
        if(startIndex > -1) {
            var endIndex = content.indexOf(" ", startIndex)
            if(endIndex == -1 ){
                endIndex =content.indexOf('\n', startIndex)
            }
            return content.substring(startIndex, if(endIndex > -1) endIndex else content.length)

        }
        return ""
    }


    fun agoToText(countSec: Int): String {
        return when (countSec) {
            in 0..60 -> "только что"
            in 61..60 * 60 -> {
                "${periodToString(countSec / 60, TimeUnit.Minutes)} назад"
            }
            in 60 * 60 + 1..24 * 60 * 60 -> {
                "${periodToString(countSec / (60 * 60), TimeUnit.Hours)} назад"
            }
            in 24 * 60 * 60 + 1..48 * 60 * 60 -> {
                "сегодня"
            }
            in 48 * 60 * 60 + 1..72 * 60 * 60 -> {
                "вчера"
            }
            else -> "давно"
        }
    }

    enum class TimeUnit {
        Minutes,
        Hours
    }


    private fun periodToString(countUnits: Int, unit: TimeUnit): String {
        val lastDigit: Int = countUnits % 10
        return "$countUnits " + when (unit) {
            TimeUnit.Minutes -> when (lastDigit) {
                0, 5, 6, 7, 8, 9 -> "минут"
                1 -> "минута"
                2, 3, 4 -> "минуты"
                else -> ""
            }
            TimeUnit.Hours -> when (lastDigit) {
                0, 5, 6, 7, 8, 9 -> "часов"
                1 -> "час"
                2, 3, 4 -> "часа"
                else -> ""
            }

        }
    }
}