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
        ButtonAction += this
    }

    /**
     * Returns the [possibilities] of details for this action.
     */
    abstract val possibilities: List<String>

    /**
     * Method invoked when the action is used.
     */
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