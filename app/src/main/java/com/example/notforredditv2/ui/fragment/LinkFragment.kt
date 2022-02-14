package com.example.notforredditv2.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notforredditv2.R
import com.example.notforredditv2.databinding.LinkFragmentBinding
import com.example.notforredditv2.ui.adapter.CommentAdapter
import com.example.notforredditv2.viewmodel.CommentViewModel
import com.example.notforredditv2.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.link_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LinkFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewmodel: CommentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: LinkFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.link_fragment, container, false)
        viewmodel = ViewModelProviders.of(this,viewModelFactory).get(CommentViewModel::class.java)
        binding.lifecycleOwner = this
        binding.commentViewModel = viewmodel
        binding.linkAuthor.text = arguments!!.getString("author")
        binding.linkTitle.text = arguments!!.getString("title")
        binding.linkScore.text = arguments!!.getInt("score").toString()
        binding.linkNumComments.text = arguments!!.getInt("num_comments").toString()
        binding.thumbnail = arguments!!.getString("thumbnail")
        binding.selftext = arguments!!.getString("selftext")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CommentAdapter()

        comment_recycler_view.layoutManager = LinearLayoutManager(context)
        comment_recycler_view.setHasFixedSize(true)
        comment_recycler_view.adapter = adapter

        val subreddit = arguments!!.getString("subreddit","")
        val article = arguments!!.getString("id", "")
        viewmodel.setArgs(subreddit,article)
        CoroutineScope(Dispatchers.Main).launch {
            viewmodel.loadComments().observe(viewLifecycleOwner, Observer { comments ->
                adapter.setComments(comments)
            })
        }

    }

}