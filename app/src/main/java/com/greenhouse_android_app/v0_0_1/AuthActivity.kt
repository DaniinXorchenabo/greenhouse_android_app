package com.greenhouse_android_app.v0_0_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.google.gson.Gson
import okhttp3.*
import kotlin.concurrent.thread


class AuthActivity : AppCompatActivity() {

    var loginView: EditText? = null
    var passwordView: EditText? = null

    val httpClient_ = OkHttpClient.Builder()
        .eventListener(object : EventListener() {
            override fun requestBodyEnd(call: Call, byteCount: Long) {
                println("requestBodyEnd: $call")
            }

            override fun responseHeadersStart(call: Call) {
                println("responseHeadersStart: $call")
            }
        })
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        loginView = findViewById(R.id.usernameInputView)
        passwordView = findViewById(R.id.passwordInputView)

//        OkHttpWebSocket()

    }

    fun sendLoginAndPassword(view: View){
        if (loginView?.text != null && passwordView?.text != null){
            thread {
                try {

                    val formBody: RequestBody = FormBody.Builder()
                        .add("username", loginView!!.text.toString())
                        .add("password", passwordView!!.text.toString()).build()

                    val request = Request.Builder()
                        .url("https://dev-api.astrokupol.com/token")
                        .post(formBody)
                        .header("Authorization", "Basic Og==")
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .build()

                    val body = httpClient_.newCall(request).execute().use {
//                        it.
                        it.body!!.string()
                    }

                    val gson = Gson()

                    val tutorial_1: OAuth2Model = gson.fromJson(body, OAuth2Model::class.java)

                    runOnUiThread {
                        Log.d("MyLog", body)
                    }

                    val mainIntent_ = Intent(this, BaseActivity::class.java)
                    mainIntent_.putExtra(BaseActivity.OAUTH2_TOKEN, tutorial_1.access_token)
                    startActivity(mainIntent_)

                } catch (e: Exception) {
                    runOnUiThread {
                        Log.d("MyLog", "Не получилось с сетью ${e.toString()}")
                    }
                    return@thread
                }

            }
        }
    }

//    object NewsApiService {
//        //creating a Network Interceptor to add api_key in all the request as authInterceptor
//        private val interceptor = Interceptor { chain ->
//            val url = chain.request().url.newBuilder().build()
//            val request = chain.request()
//                .newBuilder()
//                .url(url)
//                .build()
//            chain.proceed(request)

////    https://medium.com/hacktive-devs/making-network-calls-on-android-with-retrofit-kotlins-coroutines-72fd2594184b

////            val formBody: RequestBody = Builder()
////                .add("message", "Your message")
////                .build()
////            val request: Request = Builder()
////                .url("http://www.foo.bar/index.php")
////                .post(formBody)
////                .build()
//        }
//        // we are creating a networking client using OkHttp and add our authInterceptor.
//        private val apiClient = OkHttpClient().newBuilder().addInterceptor(interceptor).build()
//
//        private fun getRetrofit(): Retrofit {
//            return Retrofit.Builder().client(apiClient)
//                .baseUrl("http://dev-api.astrokupol.com/token")
//                .addConverterFactory(MoshiConverterFactory.create())
//                .addCallAdapterFactory(CoroutineCallAdapterFactory())
//                .build()
//        }
//
//        val auth2 = getRetrofit().toString()
//
////        val newsApi: NewsApiInterface = getRetrofit().create(NewsApiInterface::class.java)
//    }

}