package com.leafclient.keyboardPLS

import com.leafclient.trunk.Identifiable

class Keyboard<K: Any, BUTTON: Button<K>>(
        private val keyToButton: Map<K, BUTTON>,
        private val none: BUTTON
) {

    private val identifierToButton = keyToButton.values.associateBy(Identifiable::identifier)

    /**
     * Returns the [Button] instance with [identifier] or [none]
     */
    operator fun get(identifier: String) = identifierToButton[identifier] ?: none

    /**
     * Returns the [Button] instance with [key] or [none]
     */
    operator fun get(key: K) = keyToButton[key] ?: none

}