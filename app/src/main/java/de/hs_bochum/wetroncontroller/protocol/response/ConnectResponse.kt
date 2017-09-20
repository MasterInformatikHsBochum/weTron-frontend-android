package de.hs_bochum.wetroncontroller.protocol.response

import com.google.gson.annotations.SerializedName
import de.hs_bochum.wetroncontroller.protocol.Message
import de.hs_bochum.wetroncontroller.protocol.request.ConnectRequestData

/**
 * Created by sebastian on 20/09/17.
 */

class ConnectResponse(
        gameId:Int,
        playerId:Int,
        @SerializedName("v")var value: ConnectResponseData)
    :Message(9, gameId, playerId, 'g')

