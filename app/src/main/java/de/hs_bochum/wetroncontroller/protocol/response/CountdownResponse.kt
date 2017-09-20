package de.hs_bochum.wetroncontroller.protocol.response

import com.google.gson.annotations.SerializedName
import de.hs_bochum.wetroncontroller.protocol.Message


class CountdownResponse(
        playerId:Int,
        gameId:Int,
        @SerializedName("v")var value: CountdownResponseData)
    :Message(10, gameId, playerId, 'g')
