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
        registry[identifier] = this
    }

    abstract operator fun invoke(details: String)

    companion object {
        private val registry = mutableMapOf<String, ButtonAction>()

        operator fun get(identifier: String) = registry[identifier]
    }

}

inline fun createButtonAction(
    identifier: String,
    description: String = Descriptions.UNPROVIDED,
    crossinline action: (details: String) -> Unit
) = object: ButtonAction(identifier, description) {
    override fun invoke(details: String) = action(details)
}