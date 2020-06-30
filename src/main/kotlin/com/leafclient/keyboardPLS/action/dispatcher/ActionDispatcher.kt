package com.leafclient.keyboardPLS.action.dispatcher

import com.leafclient.keyboardPLS.action.ButtonAction
import com.leafclient.keyboardPLS.action.handler.ActionHandler
import com.leafclient.keyboardPLS.action.handler.ActionHandlerRegistry
import com.leafclient.keyboardPLS.action.trigger.ButtonActionTrigger

abstract class ActionDispatcher {

    /**
     * Returns the size of this [ActionDispatcher]
     */
    abstract val size: Int

    /**
     * Returns whether this dispatcher contains [action]
     */
    abstract fun contains(action: ButtonAction): Boolean

    /**
     * Invokes the first action who needs to be invoked.
     */
    abstract fun invoke(trigger: ButtonActionTrigger)

    /**
     * Returns the actions wrapped into a list
     */
    abstract fun toList(): List<ButtonAction>

    /**
     * Common function between each of our [ActionDispatcher] used to re-instantiate an new
     * yet more adapted dispatcher depending on the size of it.
     */
    fun add(element: ButtonAction): ActionDispatcher {
        if(contains(element))
            return this

        return when(size) {
            0 -> SingleActionDispatcher(element)
            else -> MultipleActionDispatcher(
                    (this as MultipleActionDispatcher).elements.also {
                        it.add(element)
                    }
            )
        }
    }

    /**
     * Common function between each of our [ActionDispatcher] used to re-instantiate an new
     * yet more adapted dispatcher depending on the size of it.
     */
    fun remove(element: ButtonAction): ActionDispatcher {
        if(!contains(element) || size == 0)
            return this

        return when(size) {
            1 -> EmptyActionDispatcher()
            2 -> SingleActionDispatcher((this as MultipleActionDispatcher).elements.minus(element).first())
            else -> MultipleActionDispatcher(
                    (this as MultipleActionDispatcher).elements.also {
                        it.remove(element)
                    }
            )
        }
    }

}

class EmptyActionDispatcher: ActionDispatcher() {

    override val size: Int
        get() = 0

    override fun invoke(trigger: ButtonActionTrigger) {}

    override fun contains(action: ButtonAction) = false

    override fun toList() = emptyList<ButtonAction>()

}

class SingleActionDispatcher(private val element: ButtonAction): ActionDispatcher() {
    override val size: Int
        get() = 1

    override fun invoke(trigger: ButtonActionTrigger) {
        ActionHandlerRegistry
                .get<ActionHandler>(element.handler)
                ?.invoke(element, trigger)
    }

    override fun contains(action: ButtonAction) = action == element

    override fun toList() = listOf(element)
}

class MultipleActionDispatcher(val elements: MutableList<ButtonAction>): ActionDispatcher() {
    override val size: Int
        get() = elements.size

    override fun invoke(trigger: ButtonActionTrigger) {
        elements.forEach {
            ActionHandlerRegistry
                    .get<ActionHandler>(it.handler)
                    ?.invoke(it, trigger)
        }
    }

    override fun contains(action: ButtonAction) = elements.contains(action)

    override fun toList() = elements
}