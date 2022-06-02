package ru.netology.nmedia.data.impl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostBinding
import ru.netology.nmedia.getStringOfCount
import kotlin.properties.Delegates

class PostsAdapter(
    private val onLikeClicked:(post:Post)->Unit,
    private val onShareClicked:(post:Post)->Unit,

):ListAdapter<Post,PostsAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(private val binding: PostBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(post: Post)= with(binding) {
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
            imageButtonFavorit.setOnClickListener {
                onLikeClicked(post)
            }
            imageButtonShare.setOnClickListener {
                onShareClicked(post)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    private object DiffCallback:DiffUtil.ItemCallback<Post>(){
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
           return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem==newItem
        }

    }
}