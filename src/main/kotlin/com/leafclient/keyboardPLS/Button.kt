package com.leafclient.keyboardPLS

import com.leafclient.keyboardPLS.registry.ButtonActionRegistry
import com.leafclient.trunk.Identifiable
import java.time.Duration

abstract class Button<K: Any>(val key: K): Identifiable {

    val actions = ButtonActionRegistry()

    private var pressInstant: Long = -1

    /**
     * Returns whether this [Button] is currently down or not.
     */
    var isDown: Boolean
        get() = pressInstant != -1L
        set(value) { pressInstant = if(value) System.currentTimeMillis() else -1L }

    /**
     * The pression time wrapped into a [Duration]
     */
    val pressureDuration: Duration
        get() = if(pressInstant == -1L) Duration.ZERO else Duration.ofMillis(System.currentTimeMillis() - pressInstant)

}