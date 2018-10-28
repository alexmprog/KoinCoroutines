package com.renovavision.koincoroutines.domain.usecase

import android.util.Log
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

abstract class UseCase<T> {

    protected var parentJob: Job = Job()
    var backgroundContext: CoroutineContext = CommonPool
    var foregroundContext: CoroutineContext = UI

    protected abstract suspend fun executeOnBackground(): T

    fun execute(onComplete: (T) -> Unit, onError: (Throwable) -> Unit) {
        parentJob.cancel()
        parentJob = Job()
        launch(foregroundContext, parent = parentJob) {
            try {
                val result = withContext(backgroundContext) {
                    executeOnBackground()
                }
                onComplete.invoke(result)
            } catch (e: CancellationException) {
                Log.d("UseCase", "canceled by user")
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    protected suspend fun <X> background(context: CoroutineContext = backgroundContext, block: suspend () -> X): Deferred<X> {
        return async(context, parent = parentJob) {
            block.invoke()
        }
    }

    fun unsubscribe() {
        parentJob.cancel()
    }

}