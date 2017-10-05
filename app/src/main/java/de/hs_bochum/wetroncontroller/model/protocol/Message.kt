package de.hs_bochum.wetroncontroller.model.protocol

import com.google.gson.annotations.SerializedName


open class Message(
        @SerializedName("e") var eventId: Int,
        @SerializedName("g") var gameId: Int,
        @SerializedName("p") var playerId: Int,
        @SerializedName("t") var type: Char = 'c'){}
//TODO check if works with incoming messages