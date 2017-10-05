package de.hs_bochum.wetron.wetroncontroller

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import de.hs_bochum.wetroncontroller.websocket.ProtocolHandler
import de.hs_bochum.wetroncontroller.R
import de.hs_bochum.wetroncontroller.websocket.WebsocketClient
import kotlinx.android.synthetic.main.activity_game.*



/**
 * Created by Sebastian on 08.09.2017.
 */
class GameActivity:AppCompatActivity(), ProtocolHandler {
    override fun onError(message: String) {
        runOnUiThread{
            Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show()
        }
        finish()

    }

    override fun onClose() {
        runOnUiThread{
            Toast.makeText(this, "Connection closed", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    var gameId:Int = 0
    var playerId = 0
    lateinit var client: WebsocketClient

    override fun onConnectResponse(success: Boolean) {
        runOnUiThread {
        if(success){
            progressMessage.text = "Wait for other players..."
            }else{
                Toast.makeText(this, "Connection refused", Toast.LENGTH_SHORT).show()
                finish()
        }

        }
    }

    override fun onCountdownStart(ms: Int) {
        runOnUiThread {
                progressMessage.visibility = View.INVISIBLE
                progressBar.visibility = View.INVISIBLE
                countdown_text.visibility=View.VISIBLE
                countdown_text.text = (ms / 1000).toString()
            if(ms == 0){
                countdown_text.visibility = View.INVISIBLE
                iv_arrow.visibility = View.VISIBLE
                btn_right.isEnabled = true
                btn_left.isEnabled = true
            }
        }
    }

    override fun onGameOver(win: Boolean) {
        runOnUiThread({
            if (win){
                Toast.makeText(this, "You WIn", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, "You Lose", Toast.LENGTH_LONG).show()
            }
        })
        client.close()
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)

        //Remove notification bar
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        this.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)


        this.setContentView(R.layout.activity_game)

        client = WebsocketClient(this)


        btn_left.setOnClickListener({ client.sendDirectionChange(playerId, gameId, 90.0)
        iv_arrow.rotation = iv_arrow.rotation - 90})
        btn_right.setOnClickListener({ client.sendDirectionChange(playerId, gameId, 270.0)
        iv_arrow.rotation = iv_arrow.rotation + 90})

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