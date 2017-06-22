package de.hs_bochum.wetron

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_we_tron.*

import hs_bochum.de.wetron.R
import hs_bochum.de.wetron.R.id.clientIdTextview
import hs_bochum.de.wetron.R.id.verbindenButton
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence



class WeTronActivity : AppCompatActivity(), MqttCallback {


    override fun connectionLost(cause: Throwable?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deliveryComplete(token: IMqttDeliveryToken?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun messageArrived(topic: String?, message: MqttMessage?) {
        Toast.makeText(this, "Message received: " + message, Toast.LENGTH_SHORT).show();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_we_tron)
        initButton()
    }

    private fun initButton() {
        verbindenButton.setOnClickListener(View.OnClickListener {
            connect();
        })
    }

    fun connect(){
        try{
            val client = MqttClient(
                    "tcp://193.175.85.50:1883", //URI
                    MqttClient.generateClientId(), //ClientId
                    MemoryPersistence()) //Persistence
            client.connect();
            clientIdTextview.setText(client.clientId);
            client.setCallback(this);
        }catch (e : Exception){
            showErrorMessage(e.message);
        }
    }

    private fun showErrorMessage(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
