package de.hs_bochum.wetroncontroller.protocol.response

import com.google.gson.annotations.SerializedName


data class ConnectResponseData(
        @SerializedName("success")var success:Boolean
)