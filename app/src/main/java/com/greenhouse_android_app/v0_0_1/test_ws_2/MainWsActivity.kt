//package com.greenhouse_android_app.v0_0_1.test_ws_2
//
//
//import android.content.Intent
////import androidx.appcompat.app.AppCompatActivity
////import android.os.Bundle
//import android.util.Log
////import android.view.View
//import android.widget.EditText
//import com.google.gson.Gson
//import okhttp3.*
//import kotlin.concurrent.thread
//
//import android.os.Bundle;
////import android.support.v4.app.
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import okhttp3.Request;
//import okhttp3.Response;
//import okio.ByteString;
////import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
//
//import okhttp3.WebSocket
//
//import okhttp3.OkHttpClient
//
//import android.R
//import android.annotation.SuppressLint
//import androidx.appcompat.app.AppCompatActivity
//
//import okhttp3.WebSocketListener
//import okio.ByteString.Companion.decodeHex
//import okhttp3.*
////import okhttp3.B
//
//
//
//class MainWsActivity: AppCompatActivity() {
//    private var start: Button? = null
//    private var output: TextView? = null
//    private var client: OkHttpClient? = null
//
//    class EchoWebSocketListener : WebSocketListener() {
//        override fun onOpen(webSocket: WebSocket, response: Response) {
//            webSocket.send("Hello, it's SSaurel !")
//            webSocket.send("What's up ?")
//            webSocket.send("deadbeef".decodeHex())
//            webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !")
//        }
//
//        override fun onMessage(webSocket: WebSocket, text: String) {
//            output("Receiving : $text")
//        }
//
//        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
//            output("Receiving bytes : " + bytes.hex())
//        }
//
//        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
//            webSocket.close(NORMAL_CLOSURE_STATUS, null)
//            output("Closing : $code / $reason")
//        }
//
//        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
//            output("Error : " + t.message)
//        }
//
//        companion object {
//            private const val NORMAL_CLOSURE_STATUS = 1000
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        start = findViewById(R.id.start) as Button?
//        output = findViewById(R.id.output) as TextView?
//        client = OkHttpClient()
//        start!!.setOnClickListener { start() }
//    }
//
//    private fun start() {
//        val request: Request = Request.Builder().url("ws://echo.websocket.org").build()
//        val listener = EchoWebSocketListener()
//        val ws = client!!.newWebSocket(request, listener)
//        client!!.dispatcher.executorService.shutdown()
//    }
//
//    companion object {
//
//        @SuppressLint("SetTextI18n")
//        fun output(txt: String) {
//            Log.d("WsLog", txt)
////        UiThreadStatement.runOnUiThread(Runnable {
////            output!!.text = """ ${output!!.text} $txt """.trimIndent()
////        })
//        }
//    }
//}