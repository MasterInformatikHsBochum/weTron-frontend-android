package de.hs_bochum.wetroncontroller.protocol.response

import com.google.gson.annotations.SerializedName
import de.hs_bochum.wetroncontroller.protocol.Message

/**
 * Created by sebastian on 20/09/17.
 */
class GameOverResponse(
        playerId:Int,
        gameId:Int,
        @SerializedName("v")var value: GameOverResponseData)
    :Message(12, gameId, playerId, 'g')

