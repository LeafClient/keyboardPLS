package com.leafclient.keyboardPLS

import com.leafclient.trunk.Identifiable

class Keyboard<K: Any, BUTTON: Button<K>>(
        private val keyToButton: Map<K, BUTTON>
) {

    private val identifierToButton = keyToButton.values.associateBy(Identifiable::identifier)

    operator fun get(identifier: String) = identifierToButton[identifier]
    operator fun get(key: K) = keyToButton[key]

}