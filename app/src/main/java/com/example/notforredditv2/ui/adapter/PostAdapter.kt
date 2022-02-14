package com.example.notforredditv2.ui.adapter

import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notforredditv2.R
import com.example.notforredditv2.databinding.PostItemBinding
import com.example.notforredditv2.util.Constants
import com.example.notforredditv2.vo.Post
import kotlinx.android.synthetic.main.post_item.view.*

class PostAdapter : ListAdapter<Post, PostAdapter.PostViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.id == newItem.id
                        && oldItem.author == newItem.author
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.url == newItem.url
                        && oldItem.subreddit == newItem.subreddit
                        && oldItem.title == newItem.title
            }
        }
    }

    private var postList = ArrayList<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding: PostItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.post_item,
            parent,
            false
        )
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    fun setPosts(posts: List<Post>) {
        this.postList = posts as ArrayList<Post>
        notifyDataSetChanged()
    }

    class PostViewHolder(private val binding: PostItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.post = post
            binding.root.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    Log.d(Constants.TAG, "onClick: Clicked!! ${post.title}")
                    val bundle = Bundle()
                    bundle.putString("id", post.id)
                    bundle.putString("author", post.author)
                    bundle.putString("title", post.title)
                    bundle.putString("thumbnail", post.thumbnail)
                    bundle.putString("selftext", post.selftext)
                    bundle.putString("subreddit", post.subreddit)
                    bundle.putInt("score", post.score)
                    bundle.putInt("num_comments", post.numComments)
                    binding.root.findNavController().navigate(R.id.articleScreen,bundle)
                }
            })
            binding.executePendingBindings()
        }
    }

}