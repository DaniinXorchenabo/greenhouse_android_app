package com.greenhouse_android_app.v0_0_1.ui.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.gson.Gson
import com.greenhouse_android_app.v0_0_1.OAuth2Model
import com.greenhouse_android_app.v0_0_1.R
import com.greenhouse_android_app.v0_0_1.databinding.FragmentDashboardBinding
//import com.greenhouse_android_app.v0_0_1.test_ws_2.MainWsActivity
import okhttp3.*
import okio.ByteString
import okio.ByteString.Companion.decodeHex

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null

    private var output_field: TextView? = null
    private var temperatureOutput: TextView? = null
    private var humidityOutput: TextView? = null

    private var client: OkHttpClient? = null
    var listener: EchoWebSocketListener? = null
    var currentWebSocketObj: WebSocket? = null

    class EchoWebSocketListener() : WebSocketListener() {

        var outputObj: DashboardFragment? = null

        constructor(output_obj: DashboardFragment) : this() {
            outputObj = output_obj
        }

        override fun onOpen(webSocket: WebSocket, response: Response) {
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            outputObj?.output(text)
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            outputObj?.output(bytes.hex())
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null)
            outputObj?.output("Closing : $code / $reason")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            outputObj?.output("Error : " + t.message)
        }

        companion object {
            private const val NORMAL_CLOSURE_STATUS = 1000
        }
    }


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        output_field = binding.testWsConnection
        temperatureOutput = binding.temperatureTextView
        humidityOutput = binding.humidityTextView
        client = OkHttpClient()

        showRandomTemperature()
        startWebSocketChannel()
        return root
    }

    fun showRandomTemperature() {
        val temperatureView: TextView = binding.temperatureTextField
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            temperatureView.text = ""
        })
    }

    fun startWebSocketChannel() {
        val request: Request =
            Request.Builder().url("ws://192.168.43.167:10010/metrics/sensors").build()
        val listener = EchoWebSocketListener(this)
        currentWebSocketObj = client!!.newWebSocket(request, listener)
        client!!.dispatcher.executorService.shutdown()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        listener?.onClosing(currentWebSocketObj!!, 1000, "Dashboard_is_closed")
    }


    @SuppressLint("SetTextI18n")
    fun output(txt: String) {
        Log.d("WsLog", txt)

        try {
            val gson = Gson()
            val dataFromGreenHouse: WsData = gson.fromJson(txt, WsData::class.java)
            getActivity()?.runOnUiThread {
                temperatureOutput?.text = getString(
                    R.string.temperature_is_,
                    dataFromGreenHouse.metrics.temperature.toString()
                )
                humidityOutput?.text =
                    getString(R.string.humidity_is_, dataFromGreenHouse.metrics.humidity.toString())
                output_field?.text = txt + "\n" + output_field?.text.toString()
            }
        } catch (e: Exception) {
            Log.d("WsLog", "Ошибка!!!" + e.toString())
        } finally {
        }
    }
}