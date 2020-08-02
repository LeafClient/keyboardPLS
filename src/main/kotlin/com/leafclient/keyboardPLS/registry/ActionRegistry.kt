package com.leafclient.keyboardPLS.registry

import com.leafclient.keyboardPLS.ButtonAction
import com.leafclient.trunk.Identifiable

class ButtonActionRegistry {

    /**
     * Contains each [ButtonActionData] applied when the key is pressed.
     */
    val pressActions   = mutableListOf<ButtonActionData>()

    /**
     * Contains each [ButtonActionData] applied when the key is released.
     */
    val releaseActions = mutableListOf<ButtonActionData>()

    /**
     * Returns a [ActionDispatcher] used to apply the actions about this registry
     */
    val pressDispatcher: ActionDispatcher
        get() = ActionDispatcher.optimizedFor(pressActions)

    /**
     * Returns a [ActionDispatcher] used to apply the actions about this registry
     */
    val releaseDispatcher: ActionDispatcher
        get() = ActionDispatcher.optimizedFor(releaseActions)

    /**
     * Adds [action] with [details] to the pressure actions
     */
    fun press(action: String, details: String) {
        pressActions += ButtonActionData(action, details)
    }

    /**
     * Adds [action] with [details] to the pressure actions
     */
    fun press(action: ButtonAction, details: String) = press(action.identifier, details)

    /**
     * Adds [action] with [details] to the release actions
     */
    fun release(action: String, details: String) {
        releaseActions += ButtonActionData(action, details)
    }

    /**
     * Adds [action] with [details] to the release actions
     */
    fun release(action: ButtonAction, details: String) = press(action.identifier, details)

}

data class ButtonActionData(
    override val identifier: String,
    val details: String
): Identifiable {

    @delegate:Transient
    val action by lazy {
        ButtonAction[identifier]
    }

    operator fun invoke() = action?.invoke(details)

}