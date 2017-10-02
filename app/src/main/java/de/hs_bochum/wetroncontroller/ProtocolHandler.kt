package de.hs_bochum.wetroncontroller

/**
 * Created by sebastian on 20/09/17.
 */

interface ProtocolHandler {
    fun onConnectResponse(success: Boolean)
    fun onCountdownStart(ms: Int)
    fun onGameOver(win: Boolean)

    fun onError(message: String)

    fun onClose()
}
