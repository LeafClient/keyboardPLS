package com.leafclient.keyboardPLS.action.handler

import com.leafclient.keyboardPLS.action.ButtonAction
import com.leafclient.keyboardPLS.action.trigger.ButtonActionTrigger
import com.leafclient.trunk.manager.LabelableManager
import com.leafclient.trunk.structure.Labelable

abstract class ActionHandler(override val label: String): Labelable {
    abstract operator fun invoke(action: ButtonAction, trigger: ButtonActionTrigger)
}

object ActionHandlerRegistry: LabelableManager()