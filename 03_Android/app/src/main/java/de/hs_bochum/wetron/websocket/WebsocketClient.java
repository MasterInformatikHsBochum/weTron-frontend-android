package de.hs_bochum.wetron.websocket;

import android.util.Log;

import java.net.URI;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

import static android.content.ContentValues.TAG;

/**
 * Created by Sebastian on 25.08.2017.
 */

public class WebsocketClient extends WebSocketListener {

    private OkHttpClient client;
    private WebSocket connection;

    private static final int NORMAL_CLOSURE_STATUS = 1000;

    public WebsocketClient(){
        client = new OkHttpClient.Builder()
                .readTimeout(0,  TimeUnit.MILLISECONDS)
                .build();
    }

    public boolean isConnected(){
        return connection != null;
    }

    public void sendMessage(String message){
        connection.send(message);
    }

    private void close(String reason) {
        connection.close(NORMAL_CLOSURE_STATUS, reason);
    }

    public void connect(String url){
        Request request = new Request.Builder().url(url).build();
        connection = client.newWebSocket(request,this);

        client.dispatcher().executorService().shutdown();
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        this.connection = webSocket;
        sendMessage("Test");
        close("Close");
    }


    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Log.i(TAG,"Receiving : " + text);
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        Log.i(TAG,"Receiving bytes : " + bytes.hex());
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        close(null);
        Log.i(TAG, code + " / " + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        Log.e(TAG, "Error: " + t.getMessage());
    }
}
