package de.hs_bochum.wetron.wetroncontroller

import android.animation.ValueAnimator
import android.app.Activity
import android.app.DialogFragment
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import de.hs_bochum.wetroncontroller.R
import de.hs_bochum.wetroncontroller.model.QrCode
import de.hs_bochum.wetroncontroller.ui.main.GameinfoDialog
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        initButton()
        animate()
    }

    private fun animate(){
        var animator:ValueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f)
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.duration = 10000L
        animator.addUpdateListener{ valueAnimator ->
            val progress = valueAnimator.getAnimatedValue() as Float
            val width = iv_bg_1.getWidth()
            val translationX = width * progress
            iv_bg_1.setTranslationX(translationX)
            iv_bg_2.setTranslationX(translationX - width)
        }
        animator.start()
    }



    private fun initButton() {
        btn_connect.setOnClickListener({
            scanCode()
        })
        btn_about.setOnClickListener({
            showAbout()
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
            var dialog:DialogFragment = GameinfoDialog()
            dialog.show(fragmentManager, "gameinfo")
            val marketUri = Uri.parse("market://details?id=com.google.zxing.client.android")
            val marketIntent = Intent(Intent.ACTION_VIEW, marketUri)
            //startActivity(marketIntent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 0) {
            if (resultCode === Activity.RESULT_OK) {
                val contents = data?.getStringExtra("SCAN_RESULT").orEmpty()
                var code:QrCode = parseScanResult(contents)
                connect(code.gameId.toString(), code.playerId.toString())
            }
            if (resultCode === Activity.RESULT_CANCELED) {
                //handle cancel
            }
        }
    }

    private fun parseScanResult(scanResult:String) : QrCode{
        var gson:Gson = GsonBuilder().create()
        var qrCode = QrCode(0,0)
        try {
            qrCode = gson.fromJson<QrCode>(scanResult, QrCode::class.java)
        }catch (e:Exception){
            showErrorMessage("Invalid qr code")
        }
        return qrCode
    }

    fun connect(gameId: String, playerId: String) {
        if(!checkCode(gameId, playerId)){
            showInvalidCodeToast()
        }else{
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("GAME_CODE", Integer.parseInt(gameId))
            intent.putExtra("PLAYER_ID", Integer.parseInt(playerId))
            startActivity(intent)
        }
    }

    private fun checkCode(gameId: String, playerId: String): Boolean{
        try{
            val code = Integer.parseInt(gameId)
            val player = Integer.parseInt(playerId)
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


    private fun showAbout(){
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_about, null)
        dialogBuilder.setView(dialogView)
        dialogBuilder.create().show()
    }
}
