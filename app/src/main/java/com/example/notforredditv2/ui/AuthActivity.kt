package com.example.notforredditv2.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.notforredditv2.AuthDialog
import com.example.notforredditv2.CustomListener
import com.example.notforredditv2.R
import com.example.notforredditv2.ServiceInterceptor
import com.example.notforredditv2.api.ApiErrorResponse
import com.example.notforredditv2.api.ApiSuccessResponse
import com.example.notforredditv2.databinding.ActivityAuthBinding
import com.example.notforredditv2.api.OAuthService
import com.example.notforredditv2.util.Constants
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthActivity : DaggerAppCompatActivity() , CustomListener{

    private lateinit var authDialog: AuthDialog

    @Inject
    lateinit var serviceInterceptor: ServiceInterceptor

    @Inject
    lateinit var oAuthService : OAuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityAuthBinding = DataBindingUtil.setContentView(this, R.layout.activity_auth)

        login_button.setOnClickListener{
            login_progress.visibility = View.VISIBLE
            authDialog = AuthDialog(this, this)
            authDialog.setCancelable(true)
            authDialog.show()
        }

    }

    override fun onCodeReceived(access_token: String?) {
        login_progress.visibility = View.VISIBLE
        if(access_token == null){
            login_progress.visibility = View.GONE
            authDialog.dismiss()
        }
        serviceInterceptor.setAccessToken(access_token!!)

        CoroutineScope(Dispatchers.IO).launch{
            val response = oAuthService.getUser()
            if(response.isSuccessful){
                val username = response.body()!!.name!!
                goToHomeActivity(username)
            }
            else{
                withContext(Main){
                    Toast.makeText(baseContext, "Error in signing in", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    private suspend fun goToHomeActivity(name : String){
        withContext(Main) {
            val intent = Intent(baseContext, HomeActivity::class.java)
            intent.putExtra("username", name)
            login_progress.visibility = View.GONE
            startActivity(intent)
        }
    }
}
