package de.hs_bochum.wetroncontroller.protocol.request

import com.google.gson.annotations.SerializedName

/**
 * Created by sebastian on 18/09/17.
 */

data class DirectionChangeRequestData(
        @SerializedName("d")val xPos:Double?
)
