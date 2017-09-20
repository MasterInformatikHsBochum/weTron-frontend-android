package de.hs_bochum.wetroncontroller.protocol.request

import com.google.gson.annotations.SerializedName

/**
 * Created by sebastian on 20/09/17.
 */
data class ConnectRequestData(
        @SerializedName("p") val playerId:Int
)