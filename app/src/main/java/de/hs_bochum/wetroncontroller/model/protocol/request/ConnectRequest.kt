package de.hs_bochum.wetron.wetroncontroller

import com.google.gson.annotations.SerializedName
import de.hs_bochum.wetroncontroller.model.protocol.Message
import de.hs_bochum.wetroncontroller.model.protocol.request.ConnectRequestData

/**
 * Created by Sebastian on 08.09.2017.
 */
class ConnectRequest(
        gameId:Int,
        playerId:Int,
        @SerializedName("v")var Value: ConnectRequestData
) : Message(0, gameId, playerId, 'c')