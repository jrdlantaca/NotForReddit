package com.example.notforredditv2

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.NonNull
import com.example.notforredditv2.api.ApiErrorResponse
import com.example.notforredditv2.api.ApiSuccessResponse
import com.example.notforredditv2.api.OAuthService
import com.example.notforredditv2.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class AuthDialog(context : Context, customListener: CustomListener) : Dialog(context){

    private var reddit_url: String = "https://www.reddit.com/api/v1/authorize.compact?" +
            "client_id=${BuildConfig.CLIENT_ID}" +
            "&response_type=code" +
            "&state=doppio" +
            "&redirect_uri=${BuildConfig.REDIRECT_URI}" +
            "&duration=permanent" +
            "&scope=identity mysubreddits edit history read"

    private lateinit var oAuthService : OAuthService
    private lateinit var access_token : String
    private var customListener = customListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_dialog)
        openAuthentication()
    }

    private fun openAuthentication() {
        val webView : WebView = findViewById(R.id.web_view)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(reddit_url)
        webView.webViewClient = object : WebViewClient(){
            var auth : Boolean = false
            lateinit var auth_code : String

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Log.d("TAG", "onPageStarted: URL : $url")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                Log.d("TAG", "Before parse $url")
                if(url!!.contains("code=") && !auth){
                    val uri = Uri.parse(url)
                    Log.d("TAG", "URI : $uri")
                    auth_code = uri.getQueryParameter("code")!!
                    Log.d("TAG", "AUTH_CODE : $auth_code")

                    val credentials : String = okhttp3.Credentials.basic(BuildConfig.CLIENT_ID, "")
                    Log.d("TAG", "CREDIENTIALS : $credentials")
                    val loggingInterceptor = HttpLoggingInterceptor()
                    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    val okHttpClient = OkHttpClient.Builder()
                        .addInterceptor(object : Interceptor{
                            @NonNull
                            override fun intercept(chain: Interceptor.Chain): Response {
                                val original = chain.request()
                                val newRequest = original.newBuilder()
                                    .addHeader("Authorization", credentials)
                                    .build()
                                return chain.proceed(newRequest)
                            }
                        })
                        .addInterceptor(loggingInterceptor)
                        .build()


                    val retrofit = Retrofit.Builder()
                        .baseUrl("https://www.reddit.com/api/v1/")
                        .client(okHttpClient)
                        .addConverterFactory(MoshiConverterFactory.create())
                        .build()

                    oAuthService = retrofit.create(OAuthService::class.java)

                   CoroutineScope(Dispatchers.IO).launch {
                        val response = oAuthService.getToken("authorization_code", auth_code, BuildConfig.REDIRECT_URI)
                        if(response.isSuccessful){
                            access_token = response.body()!!.access_token!!
                            Log.d("TAG", "ACCESS_TOKEN : $access_token")
                            withContext(Dispatchers.Main){
                                customListener.onCodeReceived(access_token)
                                dismiss()
                            }
                        }
                       else{
                            withContext(Dispatchers.Main){
                                Toast.makeText(context, "Error in signing in ", Toast.LENGTH_LONG).show()
                                dismiss()
                            }
                        }
                    }
                }
                else if(url.contains("?error")){
                    Toast.makeText(context, "Error Occurred", Toast.LENGTH_LONG).show()
                    dismiss()
                }
            }
        }
        
    }

}