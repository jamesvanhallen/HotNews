package com.jamesvanhallen.hotnews.utils

import android.util.Log
import kotlinx.coroutines.experimental.CoroutineExceptionHandler
import kotlinx.coroutines.experimental.Job
import java.util.concurrent.CancellationException
import kotlin.coroutines.experimental.CoroutineContext

class DefaultExceptionHandler : CoroutineExceptionHandler {
    override val key: CoroutineContext.Key<*>
        get() = CoroutineExceptionHandler

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        if (exception is CancellationException) return

        context[Job]?.cancel(exception)

        Log.println(Log.ERROR, "Косячная корутина Пети", Log.getStackTraceString(exception))

        val thread = Thread.currentThread()
        thread.uncaughtExceptionHandler.uncaughtException(thread, exception)
    }

}