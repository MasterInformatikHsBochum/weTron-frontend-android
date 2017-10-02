package de.hs_bochum.wetron.wetroncontroller

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import de.hs_bochum.wetroncontroller.R
import de.hs_bochum.wetroncontroller.model.QrCode
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initButton()
    }


    private fun initButton() {
        btn_connect.setOnClickListener({
            connect()
        })
        btn_scan_qrcode.setOnClickListener({
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
                val contents = data?.getStringExtra("SCAN_RESULT").orEmpty()
                var code:QrCode = parseScanResult(contents)
                et_gameid.setText(code.gameId.toString())
                et_playerid.setText(code.playerId.toString())
            }
            if (resultCode === Activity.RESULT_CANCELED) {
                //handle cancel
            }
        }
    }

    fun parseScanResult(scanResult:String) : QrCode{
        var gson:Gson = GsonBuilder().create()
        var qrCode:QrCode = QrCode(0,0)
        try {
            qrCode = gson.fromJson<QrCode>(scanResult, QrCode::class.java)
        }catch (e:Exception){
            showErrorMessage("Invalid qr code")
        }
        return qrCode
    }

    private fun connect() {

        if(!checkCode()){
            showInvalidCodeToast()
        }else{
            val intent: Intent = Intent(this, GameActivity::class.java)
            intent.putExtra("GAME_CODE", Integer.parseInt(et_gameid.text.toString()))
            intent.putExtra("PLAYER_ID", Integer.parseInt(et_playerid.text.toString()))
            startActivity(intent)
        }
    }

    private fun checkCode() : Boolean{
        try{
            val code = Integer.parseInt(et_gameid.text.toString())
            val player = Integer.parseInt(et_playerid.text.toString())
            if(code < 0 || player < 0){
                return false
            }
        }catch (e:NumberFormatException){
            return false
        }
        return true
    }

    private fun showInvalidCodeToast(){
        Toast.makeText(this, "The Ids must be positive numbers", Toast.LENGTH_SHORT).show()
    }
}
