package com.greenhouse_android_app.v0_0_1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    var test_text: TextView? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        test_text = findViewById(R.id.test_text);
        test_text?.text = "Hi"
        Log.d("MyLog", "Hi")
        thread {
            try {

                val json = URL("https://google.com").readText()
                runOnUiThread {
                    Log.d("MyLog", json)
                    test_text?.text = json
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Log.d("MyLog", "Не получилось с сетью ${e.toString()}")
                    test_text?.text = "Не получилось с сетью  ${e.toString()}"
                }
                return@thread
            }

        }
//        test_text?.setText(URL("https://google.com").readText())
    }
}