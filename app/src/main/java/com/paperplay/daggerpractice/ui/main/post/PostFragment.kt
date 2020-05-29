package com.paperplay.daggerpractice.ui.main.post

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.paperplay.daggerpractice.R
import com.paperplay.daggerpractice.data.model.table.PostsTable
import com.paperplay.daggerpractice.data.source.remote.state.Resource
import com.paperplay.daggerpractice.utils.VerticalSpaceItemDecoration
import com.paperplay.daggerpractice.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_post.*
import javax.inject.Inject

/**
 * Created by Ahmed Yusuf on 10/05/20.
 */
class PostFragment: DaggerFragment() {
    companion object {
        private const val TAG = "PostFragment"
    }

    private var errorOccured = false

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var postRecyclerAdapter: PostRecyclerAdapter

    private lateinit var viewModel: PostViewModel

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
//        recyclerView = view.findViewById(R.id.recycler_view)
//        loadingLayout = view.findViewById(R.id.loadingLayout)
        initRecycler()
    }

    private fun initRecycler(){
        recycler_view.layoutManager = LinearLayoutManager(activity)
        recycler_view.addItemDecoration(VerticalSpaceItemDecoration(15))
        recycler_view.adapter = postRecyclerAdapter
    }

    private fun subscribeObservers(){
        viewModel.observePost().removeObservers(viewLifecycleOwner)
        viewModel.observePost().observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d(TAG, "subscribeObservers: "+it.data)
                when(it.status){
                    Resource.Status.LOADING -> {
                        txtLoadingTitle.text = getString(R.string.txtLoading)
                    }
                    Resource.Status.SUCCESS -> {
                        recycler_view.visibility = View.VISIBLE
                        if(!errorOccured) loadingLayout.visibility = View.GONE
                        postRecyclerAdapter.setPosts(it.data as List<PostsTable>)
                    }
                    Resource.Status.ERROR -> {
                        loadingLayout.setBackgroundColor(ResourcesCompat.getColor(resources,
                            R.color.colorError, null))
                        txtLoadingTitle.text = it.message
                    }
                }
            }
        })
        viewModel.error.observe(
            this,
            Observer {
                if(it.startsWith("Error: ")){
                    loadingLayout.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorError, null))
                    txtLoadingTitle.text = it
                    errorOccured = true
                }else{
                    errorOccured = false
                    loadingLayout.visibility = View.GONE
                }
            }
        )
    }

}