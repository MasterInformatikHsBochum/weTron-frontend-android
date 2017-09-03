package de.hs_bochum.wetron.ui.game

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import de.hs_bochum.wetron.websocket.EchoWebSocketListener
import de.hs_bochum.wetron.websocket.WebsocketClient
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

/**
 * Created by Sebastian on 03.09.2017.
 */
class GameActivity : AppCompatActivity(){

    private var client: OkHttpClient? = OkHttpClient()
    var ws: WebSocket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startHandshake()
    }

    fun startHandshake(){

        val request = Request.Builder().url("wss://echo.websocket.org").build()
        val listener = EchoWebSocketListener()
        ws = client?.newWebSocket(request, listener)

        client?.dispatcher()?.executorService()?.shutdown()

        ws?.send("Test")
    }


}