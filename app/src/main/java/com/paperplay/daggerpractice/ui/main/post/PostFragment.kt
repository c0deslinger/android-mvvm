package com.paperplay.daggerpractice.ui.main.post

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paperplay.daggerpractice.R
import com.paperplay.daggerpractice.model.Post
import com.paperplay.daggerpractice.network.state.Resource
import com.paperplay.daggerpractice.utils.VerticalSpaceItemDecoration
import com.paperplay.daggerpractice.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Created by Ahmed Yusuf on 10/05/20.
 */
class PostFragment: DaggerFragment() {
    companion object {
        private const val TAG = "PostFragment"
    }

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var postRecyclerAdapter: PostRecyclerAdapter

    lateinit var viewModel: PostViewModel
    lateinit var recyclerView: RecyclerView

    lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(PostViewModel::class.java)
        subscribeObservers()
        recyclerView = view.findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)
        initRecycler()
    }

    fun initRecycler(){
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addItemDecoration(VerticalSpaceItemDecoration(15))
        recyclerView.adapter = postRecyclerAdapter
    }

    fun subscribeObservers(){
        viewModel.observePost().removeObservers(viewLifecycleOwner)
        viewModel.observePost().observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d(TAG, "subscribeObservers: "+it.data)
                when(it.status){
                    Resource.Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                    Resource.Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        postRecyclerAdapter.setPosts(it.data as List<Post>)
                    }
                    Resource.Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        recyclerView.visibility = View.GONE
                        Log.e(TAG, "subscribeObservers: "+it.message )
                    }
                }
            }
        })
    }

}