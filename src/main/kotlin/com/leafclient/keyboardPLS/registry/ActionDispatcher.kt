package com.leafclient.keyboardPLS.registry

abstract class ActionDispatcher {
    abstract operator fun invoke()

    companion object {
        fun optimizedFor(data: List<ButtonActionData>) = when(data.size) {
            0    -> EmptyActionDispatcher()
            1    -> SingleActionDispatcher(data.first())
            else -> OptimizedActionDispatcher(data)
        }
    }
}

class EmptyActionDispatcher: ActionDispatcher() {
    override fun invoke() = Unit
}

class SingleActionDispatcher(private val action: ButtonActionData): ActionDispatcher() {
    override fun invoke() {
        action()
    }
}

class OptimizedActionDispatcher(private val actions: List<ButtonActionData>): ActionDispatcher() {
    override fun invoke() {
        val size = actions.size
        for(i in 0 until size) {
            actions[i]()
        }
    }
}