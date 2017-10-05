package de.hs_bochum.wetroncontroller.model.protocol.response

import com.google.gson.annotations.SerializedName
import de.hs_bochum.wetroncontroller.model.protocol.Message

/**
 * Created by sebastian on 20/09/17.
 */
class GameOverResponse(
        playerId:Int,
        gameId:Int,
        @SerializedName("v")var value: GameOverResponseData)
    :Message(5, gameId, playerId, 'g')

