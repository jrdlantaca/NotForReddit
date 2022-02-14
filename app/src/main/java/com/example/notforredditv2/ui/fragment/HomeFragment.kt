package com.example.notforredditv2.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notforredditv2.R
import com.example.notforredditv2.databinding.HomeFragmentBinding
import com.example.notforredditv2.ui.adapter.PostAdapter
import com.example.notforredditv2.viewmodel.PostViewModel
import com.example.notforredditv2.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeFragment : DaggerFragment(){

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewmodel : PostViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : HomeFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container,false)
        viewmodel = ViewModelProviders.of(this, viewModelFactory).get(PostViewModel::class.java)
        binding.lifecycleOwner = this
        binding.postViewModel = viewmodel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PostAdapter()

        post_recyclerview.layoutManager = LinearLayoutManager(context)
        post_recyclerview.setHasFixedSize(true)
        post_recyclerview.adapter = adapter

        CoroutineScope(Dispatchers.Main).launch {
            viewmodel.loadPosts().observe(viewLifecycleOwner, Observer { posts ->
                adapter.setPosts(posts)
            })
        }
    }


}