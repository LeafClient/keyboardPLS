package com.leafclient.keyboardPLS.supply

import com.leafclient.keyboardPLS.Button

abstract class ButtonSupplier {
    abstract fun supply(): Map<String, Button>
}

/**
 * Returns each [Button] available in the current environment.
 */
typealias LambdaSupplier = () -> Map<String, Button>

object ButtonSupplierFactory {
    inline fun create(crossinline supplier: LambdaSupplier) = object: ButtonSupplier() {
        override fun supply() = supplier()
    }
}