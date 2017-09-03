package de.hs_bochum.wetron.ui.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_we_tron.*

import hs_bochum.de.wetron.R
import android.content.Intent
import android.net.Uri
import android.app.Activity
import de.hs_bochum.wetron.ui.game.GameActivity


class WeTronActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_we_tron)

        initButton()
    }

    private fun initButton() {
        verbindenButton.setOnClickListener(View.OnClickListener {
            connect()
        })
        btn_qr_code.setOnClickListener(View.OnClickListener {
            scanCode()
        })
    }

    private fun showErrorMessage(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private fun scanCode(){
        try {
            val intent = Intent("com.google.zxing.client.android.SCAN")
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE") // "PRODUCT_MODE for bar codes

            startActivityForResult(intent, 0)
        } catch (e: Exception) {
            val marketUri = Uri.parse("market://details?id=com.google.zxing.client.android")
            val marketIntent = Intent(Intent.ACTION_VIEW, marketUri)
            startActivity(marketIntent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 0) {
            if (resultCode === Activity.RESULT_OK) {
                val contents = data?.getStringExtra("SCAN_RESULT")
                et_game_code.setText(contents.toString())
            }
            if (resultCode === Activity.RESULT_CANCELED) {
                //handle cancel
            }
        }
    }

    private fun connect() {
        val intent:Intent = Intent(this, GameActivity::class.java)
        intent.putExtra("GAME_CODE", 123)
        startActivity(intent)
    }
}

