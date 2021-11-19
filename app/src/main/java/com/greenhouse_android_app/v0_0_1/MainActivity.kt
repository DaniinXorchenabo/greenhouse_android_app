package com.greenhouse_android_app.v0_0_1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    companion object {
        const val OAUTH2_TOKEN = ""
    }

    var test_text: TextView? = null
    var oauth2_token: String? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        oauth2_token = intent.getStringExtra(OAUTH2_TOKEN)


        test_text = findViewById(R.id.test_text);
        test_text?.text = "Hi"
        Log.d("MyLog", "Hi")
        test_text?.text = oauth2_token
//        test_text?.setText(URL("https://google.com").readText())
    }
}