package com.jamesvanhallen.hotnews.strategy

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.ViewCommand
import com.arellomobile.mvp.viewstate.strategy.StateStrategy

/**
 * Created by james on 10.03.2018.
 */
class AddToEndSingleByTagStrategy : StateStrategy {
    override fun <T : MvpView> beforeApply(currentState: MutableList<ViewCommand<T>>, incomingCommand: ViewCommand<T>) {
        val iterator = currentState.iterator()

        while (iterator.hasNext()) {
            val entry = iterator.next()

            if (entry.tag == incomingCommand.tag) {
                iterator.remove()
                break
            }
        }

        currentState.add(incomingCommand)
    }

    override fun <T : MvpView> afterApply(currentState: List<ViewCommand<T>>, incomingCommand: ViewCommand<T>) {
        // pass
    }
}
