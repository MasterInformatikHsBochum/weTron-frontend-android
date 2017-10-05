package de.hs_bochum.wetroncontroller.model.protocol.response

import com.google.gson.annotations.SerializedName
import de.hs_bochum.wetroncontroller.model.protocol.Message

/**
 * Created by sebastian on 20/09/17.
 */

class ConnectResponse(
        gameId:Int,
        playerId:Int,
        @SerializedName("v")var value: ConnectResponseData)
    :Message(8, gameId, playerId, 'g')

