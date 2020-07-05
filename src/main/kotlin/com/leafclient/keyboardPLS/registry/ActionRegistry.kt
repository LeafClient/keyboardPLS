package com.leafclient.keyboardPLS.registry

import com.leafclient.keyboardPLS.ButtonAction
import com.leafclient.trunk.Identifiable

class ButtonActionRegistry {

    val pressActions   = mutableListOf<ButtonActionEntry>()
    val releaseActions = mutableListOf<ButtonActionEntry>()

    val pressDispatcher: ActionDispatcher
        get() = ActionDispatcher.optimizedFor(pressActions)

    val releaseDispatcher: ActionDispatcher
        get() = ActionDispatcher.optimizedFor(releaseActions)

}

data class ButtonActionEntry(
    override val identifier: String,
    val details: String
): Identifiable {

    @delegate:Transient
    val action by lazy {
        ButtonAction[identifier]
    }

    operator fun invoke() = action?.invoke(details)

}