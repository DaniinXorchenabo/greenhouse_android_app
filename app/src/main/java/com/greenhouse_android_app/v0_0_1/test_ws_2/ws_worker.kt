package com.greenhouse_android_app.v0_0_1.test_ws_2
//package com.greenhouse_android_app.v0_0_1.test_ws_2
//
////import com.greenhouse_android_app.v0_0_1.test_ws_2.MainWsActivity.Companion.output
//import okhttp3.Response
//import okhttp3.WebSocket
//
//import okhttp3.WebSocketListener
//import okio.ByteString
//import okio.ByteString.Companion.decodeHex
//
//open class EchoWebSocketListener : WebSocketListener() {
//    override fun onOpen(webSocket: WebSocket, response: Response) {
//        webSocket.send("Hello, it's SSaurel !")
//        webSocket.send("What's up ?")
//        webSocket.send("deadbeef".decodeHex())
//        webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !")
//    }
//
//    override fun onMessage(webSocket: WebSocket, text: String) {
//        output("Receiving : $text")
//    }
//
//    override fun  onMessage(webSocket: WebSocket, bytes: ByteString) {
//        output("Receiving bytes : " + bytes.hex())
//    }
//
//    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
//        webSocket.close(NORMAL_CLOSURE_STATUS, null)
//        output("Closing : $code / $reason")
//    }
//
//    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
//        output("Error : " + t.message)
//    }
//
//    companion object {
//        private const val NORMAL_CLOSURE_STATUS = 1000
//    }
//}