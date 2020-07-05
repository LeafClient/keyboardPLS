package com.leafclient.keyboardPLS.registry

import sun.invoke.empty.Empty

abstract class ActionDispatcher {
    abstract operator fun invoke()

    companion object {
        fun optimizedFor(entries: List<ButtonActionEntry>) = when(entries.size) {
            0    -> EmptyActionDispatcher()
            1    -> SingleActionDispatcher(entries.first())
            else -> OptimizedActionDispatcher(entries)
        }
    }
}

class EmptyActionDispatcher: ActionDispatcher() {
    override fun invoke() = Unit
}

class SingleActionDispatcher(private val action: ButtonActionEntry): ActionDispatcher() {
    override fun invoke() {
        action()
    }
}

class OptimizedActionDispatcher(private val actions: List<ButtonActionEntry>): ActionDispatcher() {
    override fun invoke() {
        val size = actions.size
        for(i in 0 until size) {
            actions[i]()
        }
    }
}