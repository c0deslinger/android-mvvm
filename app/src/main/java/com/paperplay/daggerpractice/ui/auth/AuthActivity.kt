package com.paperplay.daggerpractice.ui.auth

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.RequestManager
import com.paperplay.daggerpractice.R
import com.paperplay.daggerpractice.data.model.response.User
import com.paperplay.daggerpractice.data.source.remote.state.AuthResource
import com.paperplay.daggerpractice.ui.main.MainActivity
import com.paperplay.daggerpractice.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class  AuthActivity : DaggerAppCompatActivity() {
    companion object {
        private const val TAG = "AuthActivity"
    }

    /** Provided rom AppModule **/
    @Inject
    lateinit var logoFromAppModule: Drawable
    @Inject
    lateinit var glideReqManager: RequestManager

    /** Provided rom ViewModelFactoryModule **/
    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        val edtUser = findViewById<EditText>(R.id.user_id_input)
        val btnLogin = findViewById<Button>(R.id.login_button)

        glideReqManager.load(logoFromAppModule).into(findViewById(R.id.login_logo))

        val viewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(AuthViewModel::class.java)

        if(viewModel.isAlreadyLoggedIn()){
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnLogin.setOnClickListener {
            val userId = edtUser.text.toString()
            if(!TextUtils.isEmpty(userId)){
                //lakukan api call
                viewModel.authenticateWithId(userId.toInt())
            }
        }

        //observe livedata auth user
        viewModel.observeUser().observe(this,
            Observer<AuthResource<User>> { result ->
                result?.let {
                    when(it.status){
                        AuthResource.AuthStatus.LOADING -> {
                            progressBar.visibility = View.VISIBLE
                            Log.d(TAG, "Loading")
                        }
                        AuthResource.AuthStatus.AUTHENTICATED -> {
                            progressBar.visibility = View.GONE
                            val intent = Intent(baseContext, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                            Log.d(TAG, "Authenticated: "+it.data?.email)
                        }
                        AuthResource.AuthStatus.ERROR -> {
                            progressBar.visibility = View.GONE
                            Log.d(TAG, "Error: "+it.message)
                        }
                        AuthResource.AuthStatus.NOT_AUTHENTICATED -> {
                            progressBar.visibility = View.GONE
                            Log.d(TAG, "Logged Out")
                        }
                    }
                }
            })
    }
}
