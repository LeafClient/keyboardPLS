package com.leafclient.keyboardPLS

import com.leafclient.keyboardPLS.action.ButtonAction
import com.leafclient.trunk.kotlin.currentTimeMillis
import com.leafclient.trunk.structure.Labelable
import com.leafclient.trunk.structure.Toggleable
import java.time.Duration

/**
 * Represents a [Button] to keyboardPLS. A key is only recognized using its [label] since
 * there are various keyboard layouts and they are all supported using the translations in Minecraft.
 *
 * It implements both [Labelable] and [Toggleable] for Leaf.
 */
data class Button(override val label: String): Labelable, Toggleable {

    /**
     * Instant when this [Button] has been pressed.
     */
    private var pressInstant: Long = -1

    /**
     * Returns whether this button is down or not and allow
     * you to set its current state.
     */
    var isDown: Boolean
        get() = pressInstant != -1L
        set(value) {
            pressInstant = if(value) currentTimeMillis else -1L
        }

    /**
     * Same as [isDown] (for [Toggleable] implementation)
     */
    override var isRunning: Boolean
        get() = pressInstant != -1L
        set(value) {
            pressInstant = if(value) currentTimeMillis else -1L
        }

    /**
     * Returns the duration of this pressure
     */
    val pressureDuration: Duration
        get() = if(pressInstant == -1L)
            Duration.ZERO
        else
            Duration.ofMillis(currentTimeMillis - pressInstant)

    /**
     * Returns the list of actions for this button
     */
    val actions: List<ButtonAction>
        get() = Keyboard.getActions(this)

    /**
     * Registers [action] to this button
     */
    fun registerAction(action: ButtonAction) = Keyboard.registerAction(this, action)

    /**
     * Registers [action] to this button
     */
    fun unregisterAction(action: ButtonAction) = Keyboard.unregisterAction(this, action)

    /**
     * Checks whether [other] is a [Button] with the same [label].
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Button

        if (label != other.label) return false

        return true
    }

    /**
     * Returns [label] hash code.
     */
    override fun hashCode(): Int {
        return label.hashCode()
    }

    companion object {
        fun find(label: String) = Keyboard.findButton(label)
    }

}