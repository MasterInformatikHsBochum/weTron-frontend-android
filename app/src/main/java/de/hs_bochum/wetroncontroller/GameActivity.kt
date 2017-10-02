package de.hs_bochum.wetron.wetroncontroller

import android.app.ProgressDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Looper
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import de.hs_bochum.wetroncontroller.ProtocolHandler
import de.hs_bochum.wetroncontroller.R
import de.hs_bochum.wetroncontroller.WebsocketClient
import kotlinx.android.synthetic.main.activity_game.*

/**
 * Created by Sebastian on 08.09.2017.
 */
class GameActivity:AppCompatActivity(), ProtocolHandler {
    override fun onError(message: String) {
        Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show()
    }

    override fun onClose() {
        runOnUiThread{
            Toast.makeText(this, "Connection closed", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    var gameId:Int = 0
    var playerId = 0
    lateinit var client:WebsocketClient
    lateinit var countDownDialog:AlertDialog

    override fun onConnectResponse(success: Boolean) {
        if(success){
            runOnUiThread {
                progressMessage.setText("Warte auf andere Spieler...")
            }

        }
    }

    override fun onCountdownStart(ms: Int) {
        runOnUiThread {
            if(!countDownDialog.isShowing){
                progressMessage.visibility = View.INVISIBLE
                progressBar.visibility = View.INVISIBLE
                countDownDialog.show()
            }
            countDownDialog.setTitle("Game starts in: " + (ms / 1000).toString())
            if(ms == 0){
                countDownDialog.dismiss()
                btn_right.isEnabled = true
                btn_left.isEnabled = true
            }
        }
    }

    override fun onGameOver(win: Boolean) {
        Toast.makeText(this, "Game Over", Toast.LENGTH_LONG).show()
        client.close()
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        client = WebsocketClient(this)

        setContentView(R.layout.activity_game)

        btn_left.setOnClickListener(View.OnClickListener { client.sendDirectionChange(playerId, gameId, 90.0)})
        btn_right.setOnClickListener(View.OnClickListener { client.sendDirectionChange(playerId, gameId, 270.0) })

        gameId  =intent.getIntExtra("GAME_CODE", -1)
        playerId = intent.getIntExtra("PLAYER_ID", -1)
        startConnection(gameId, playerId)

        countDownDialog = AlertDialog.Builder(this).create()
        countDownDialog.setTitle("Game Start in:")
    }

    private fun startConnection(code: Int, player: Int){
        progressBar.visibility = View.VISIBLE
        progressBar.animate()
        progressMessage.setText("Connecting...")
        client.init()
        client.sendConnect(code,  player)
    }

    override fun onStop() {
        super.onStop()
        client.close()
    }
}