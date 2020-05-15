package com.paperplay.daggerpractice.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.paperplay.daggerpractice.R
import com.paperplay.daggerpractice.network.state.AuthResource
import com.paperplay.daggerpractice.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Created by Ahmed Yusuf on 10/05/20.
 */
class ProfileFragment : DaggerFragment() {

    /** Provided rom ViewModelFactoryModule **/
    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private lateinit var txtUsername: TextView
    private lateinit var txtEmail: TextView
    private lateinit var txtWebsite: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(ProfileViewModel::class.java)

        txtUsername = view.findViewById(R.id.username)
        txtEmail = view.findViewById(R.id.email)
        txtWebsite = view.findViewById(R.id.website)

        subscribeObserver()
    }

    private fun subscribeObserver(){
        /**
         * Hapus observer karena Fragment mempunyai Lifecycle sendiri
         */
        viewModel.observeSession().removeObservers(viewLifecycleOwner)
        viewModel.observeSession().observe(this, Observer {
            when(it.status){
                AuthResource.AuthStatus.AUTHENTICATED -> {
                    txtUsername.text = it.data?.username
                    txtEmail.text = it.data?.email
                    txtWebsite.text = it.data?.website
                }
                else -> {
                    Toast.makeText(context, "Couldn't get session: "+it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}