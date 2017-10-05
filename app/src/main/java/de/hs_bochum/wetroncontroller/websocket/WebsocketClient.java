package de.hs_bochum.wetroncontroller.websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.hs_bochum.wetron.wetroncontroller.ConnectRequest;
import de.hs_bochum.wetroncontroller.model.protocol.Message;
import de.hs_bochum.wetroncontroller.model.protocol.request.ConnectRequestData;
import de.hs_bochum.wetroncontroller.model.protocol.request.DirectionChangeRequest;
import de.hs_bochum.wetroncontroller.model.protocol.request.DirectionChangeRequestData;
import de.hs_bochum.wetroncontroller.model.protocol.response.ConnectResponse;
import de.hs_bochum.wetroncontroller.model.protocol.response.CountdownResponse;
import de.hs_bochum.wetroncontroller.model.protocol.response.GameOverResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by Sebastian on 08.09.2017.
 */

public class WebsocketClient extends WebSocketListener{

    private static final int NORMAL_CLOSURE_STATUS = 1000;
    private boolean countdownStarted = false;
    WebSocket ws;
    ProtocolHandler callback;

    public WebsocketClient(ProtocolHandler callback) {
        this.callback = callback;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        ws = webSocket;
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        System.out.println("Receiving: " + text);
        Gson gson = new GsonBuilder().create();
        Message message = gson.fromJson(text, Message.class);
        switch(message.getEventId()){
            case 8:
                ConnectResponse connectResponse = gson.fromJson(text, ConnectResponse.class);
                callback.onConnectResponse(connectResponse.getValue().getSuccess());
                break;
            case 4:
                CountdownResponse countdownResponse = gson.fromJson(text, CountdownResponse.class);
                    callback.onCountdownStart(countdownResponse.getValue().getMs());
                break;
            case 5:
                GameOverResponse gameOverResponse = gson.fromJson(text, GameOverResponse.class);
                callback.onGameOver(gameOverResponse.getValue().getWin());
                break;
            case 7:
                break;
        }
        System.out.print(message);
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        System.out.println("Receiving: " + bytes.hex());
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
        callback.onClose();
        System.out.println("Closing: " + code + " " + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        callback.onError(t.getMessage());
        t.printStackTrace();
    }

    public void init(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("wss://wetron.tk/websocket/").build();
        ws = client.newWebSocket(request, this);

        client.dispatcher().executorService().shutdown();
    }

    public void sendMessage(String message){
        System.out.println("Sending: " + message);
        ws.send(message);
    }

    public void close() {
        ws.close(NORMAL_CLOSURE_STATUS, "Goodbye!");
    }

    public void sendConnect(int gameId, int playerId) {
        Message message = new ConnectRequest(gameId, playerId, new ConnectRequestData(playerId));
        sendMessage(message);
    }

    public void sendDirectionChange(int gameId, int playerId, double direction){
        Message message = new DirectionChangeRequest(gameId, playerId, new DirectionChangeRequestData(direction));
        sendMessage(message);
    }


    private void sendMessage(Message message){
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(message);
        sendMessage(json);
    }

}
