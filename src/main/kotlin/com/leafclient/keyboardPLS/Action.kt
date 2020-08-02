package com.leafclient.keyboardPLS

import com.leafclient.trunk.Describable
import com.leafclient.trunk.Descriptions
import com.leafclient.trunk.Identifiable
import com.leafclient.trunk.checkIfIdentifiable

abstract class ButtonAction(
    final override val identifier: String,
    override val description: String = Descriptions.UNPROVIDED
): Identifiable, Describable {

    init {
        checkIfIdentifiable()
    }

    abstract operator fun invoke(details: String)

    companion object {
        private val registry = mutableMapOf<String, ButtonAction>()

        operator fun get(identifier: String) = registry[identifier]
        operator fun plusAssign(action: ButtonAction) {
            registry[action.identifier] = action
        }
        operator fun plusAssign(actions: List<ButtonAction>) {
            registry.putAll(actions.associateBy { it.identifier })
        }
    }

}

/**
 * Creates a new [ButtonAction] instance with provided information.
 */
inline fun createButtonAction(
    identifier: String,
    description: String = Descriptions.UNPROVIDED,
    crossinline action: (details: String) -> Unit
) = object: ButtonAction(identifier, description) {
    override fun invoke(details: String) = action(details)
}