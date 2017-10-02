package de.hs_bochum.wetroncontroller.protocol.request

import com.google.gson.annotations.SerializedName
import de.hs_bochum.wetroncontroller.protocol.Message

/**
 * Created by sebastian on 20/09/17.
 */
class DirectionChangeRequest(
        playerId:Int,
        gameId:Int,
        @SerializedName("v")var value: DirectionChangeRequestData)
    :Message(6, gameId, playerId, 'c')

