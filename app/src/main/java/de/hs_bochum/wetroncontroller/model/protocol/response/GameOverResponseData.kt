package de.hs_bochum.wetroncontroller.model.protocol.response

import com.google.gson.annotations.SerializedName

/**
 * Created by sebastian on 20/09/17.
 */
data class GameOverResponseData(
        @SerializedName("win")var win:Boolean
)