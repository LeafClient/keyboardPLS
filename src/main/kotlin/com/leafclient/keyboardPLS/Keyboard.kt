package com.leafclient.keyboardPLS

import com.leafclient.keyboardPLS.action.ButtonAction
import com.leafclient.keyboardPLS.action.dispatcher.ActionDispatcher
import com.leafclient.keyboardPLS.action.dispatcher.EmptyActionDispatcher
import com.leafclient.keyboardPLS.action.dispatcher.SingleActionDispatcher
import com.leafclient.keyboardPLS.supply.ButtonSupplier
import com.leafclient.keyboardPLS.action.trigger.ButtonActionTrigger

/**
 * Represents the [Keyboard] of the user and provides access to each buttons actions
 * (mouse buttons are also contained inside of [Keyboard]).
 */
object Keyboard {

    private val DISPATCHERS = mutableMapOf<Button, ActionDispatcher>()

    /**
     * Supplies the [Keyboard] with the [Button] instances to work with current
     * environment.
     */
    fun supply(supplier: ButtonSupplier) = BUTTONS.putAll(supplier.supply())

    /**
     * Value returned if the button is unknown
     */
    val UNKNOWN_BUTTON = Button("none")

    /**
     * Associates a string to a [Button] instance.
     */
    private val BUTTONS = mutableMapOf<String, Button>()

    /**
     * Returns the [Button] instance by its [key]
     */
    fun findButton(key: String) = BUTTONS[key] ?: UNKNOWN_BUTTON

    /**
     * Returns the actions for [button]
     */
    fun getActions(button: Button) = DISPATCHERS[button]?.toList() ?: emptyList()

    /**
     * Registers the [action] for [button] into the dispatchers.
     */
    fun registerAction(button: Button, action: ButtonAction) {
        val dispatcher = DISPATCHERS[button]
        if(dispatcher == null)
            DISPATCHERS[button] = SingleActionDispatcher(action)
        else
            DISPATCHERS[button] = dispatcher.add(action)
    }

    /**
     * Unregisters the [action] for [button] into the dispatchers.
     */
    fun unregisterAction(button: Button, action: ButtonAction) {
        val dispatcher = DISPATCHERS[button] ?: return
        DISPATCHERS[button] = dispatcher.remove(action)
    }

    /**
     * Defines [button] as pressed and runs the actions associated to it.
     */
    fun input(button: Button, type: ButtonActionTrigger) {
        button.isDown = type == ButtonActionTrigger.PRESS
        button.actions.forEach {
            it(type)
        }
    }

}