package com.greenhouse_android_app.v0_0_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.URI
import java.net.URL
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


        thread {
            try {

                val formBody: RequestBody = FormBody.Builder()
                    .add("username", "test_user")
                    .add("password", "123456789").build()

                val request = Request.Builder()
                    .url("http://dev-api.astrokupol.com/token")
                    .post(formBody)
                    .header("Authorization", "Basic Og==")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .build()

                val body = httpClient_.newCall(request).execute().use {
                    it.body!!.string()
                }

                runOnUiThread {
                    Log.d("MyLog", body)
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Log.d("MyLog", "Не получилось с сетью ${e.toString()}")
                }
                return@thread
            }

        }
    }

    fun sendLoginAndPassword(view: View){
        if (loginView?.text != null && passwordView?.text != null){
            thread {
                try {

                    val formBody: RequestBody = FormBody.Builder()
                        .add("username", "test_user")
                        .add("password", "123456789").build()

                    val request = Request.Builder()
                        .url("http://dev-api.astrokupol.com/token")
                        .post(formBody)
                        .header("Authorization", "Basic Og==")
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .build()

                    val body = httpClient_.newCall(request).execute().use {
                        it.body!!.string()
                    }

                    runOnUiThread {
                        Log.d("MyLog", body)
                    }
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
//
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