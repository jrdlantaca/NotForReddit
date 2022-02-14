package com.example.notforredditv2.ui.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notforredditv2.R
import com.example.notforredditv2.databinding.CommentItemBinding
import com.example.notforredditv2.vo.Comment

class CommentAdapter : ListAdapter<Comment, CommentAdapter.CommentViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Comment>() {
            override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem.id == newItem.id
                        && oldItem.author == newItem.author
            }

            override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem.body == newItem.body
                        && oldItem.subreddit == newItem.subreddit
            }
        }
    }

    private var commentList = ArrayList<Comment>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentViewHolder {
        val binding: CommentItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.comment_item,
            parent,
            false
        )
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentAdapter.CommentViewHolder, position: Int) {
        holder.bind(commentList[position])
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    fun setComments(comments: List<Comment>) {
        this.commentList = comments as ArrayList<Comment>
        notifyDataSetChanged()
    }

    class CommentViewHolder(private val binding: CommentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            binding.comment = comment
            binding.executePendingBindings()
        }
    }
}