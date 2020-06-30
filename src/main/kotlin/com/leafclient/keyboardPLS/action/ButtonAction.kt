package com.leafclient.keyboardPLS.action

import com.leafclient.keyboardPLS.action.handler.ActionHandler
import com.leafclient.keyboardPLS.action.handler.ActionHandlerRegistry
import com.leafclient.keyboardPLS.action.trigger.ButtonActionTrigger
import com.leafclient.trunk.structure.Describable
import com.leafclient.trunk.structure.Labelable

/**
 * Contains information about an action performed when a key is pressed.
 */
data class ButtonAction(
    override val label: String,
    override val description: String = Describable.NO_DESCRIPTION_SET,
    /**
     * Contains the information provided to the handler to understand how the action should be performed,
     * for example: A toggle action will contain the mod name in this property to allow an easier serialization.
     */
    val action: String,
    val handler: String
): Labelable, Describable {

    @Transient private var handlerInstance: ActionHandler? = null

    operator fun invoke(trigger: ButtonActionTrigger) {
        if(handlerInstance == null)
            handlerInstance = ActionHandlerRegistry[handler]

        handlerInstance?.invoke(this, trigger)
    }

}