package de.hs_bochum.wetroncontroller;

/**
 * Created by sebastian on 20/09/17.
 */

public interface ProtocolHandler {
    void onConnectResponse(boolean success);
    void onCountdownStart(int ms);
    void onGameOver(boolean win);
}
