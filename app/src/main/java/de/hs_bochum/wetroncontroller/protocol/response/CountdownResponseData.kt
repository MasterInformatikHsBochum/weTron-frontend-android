package de.hs_bochum.wetroncontroller.protocol.response

import com.google.gson.annotations.SerializedName

/**
 * Created by sebastian on 20/09/17.
 */
data class CountdownResponseData(
        @SerializedName("countdown-ms")var ms:Int
)