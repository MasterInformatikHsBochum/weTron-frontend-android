package de.hs_bochum.wetron.wetroncontroller

import android.app.ProgressDialog
import android.os.Bundle
import android.os.CountDownTimer
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

    var gameId:Int = 0
    var playerId = 0
    lateinit var client:WebsocketClient

    override fun onConnectResponse(success: Boolean) {
        if(success){
            progressMessage.setText("Warte auf andere Spieler...")
        }
    }

    override fun onCountdownStart(ms: Int) {
        object : CountDownTimer(ms.toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                progressMessage.setText("seconds remaining: " + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                progressBar.visibility = View.INVISIBLE
                btn_right.isEnabled = true
                btn_left.isEnabled = true
            }
        }.start()

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

        btn_left.setOnClickListener(View.OnClickListener { client.sendDirectionChange(playerId, gameId, 0.75)})
        btn_right.setOnClickListener(View.OnClickListener { client.sendDirectionChange(playerId, gameId, 0.25) })

        gameId  =intent.getIntExtra("GAME_CODE", -1)
        playerId = intent.getIntExtra("PLAYER_ID", -1)
        startConnection(gameId, playerId)
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