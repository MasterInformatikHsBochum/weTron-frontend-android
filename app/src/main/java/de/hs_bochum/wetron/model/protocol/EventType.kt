package de.hs_bochum.wetron.model.protocol

/**
 * Created by Sebastian on 27.08.2017.
 */

enum class EventType private constructor(val value: Int) {
    CONNECT(0),
    DISCONNECT(1),
    STARTUP(2),
    STARTUP_ACK(3),
    GAME_START(4),
    GAME_END(5),
    CHANGE_DIRECTION(6),
    POSITION(7);
}
