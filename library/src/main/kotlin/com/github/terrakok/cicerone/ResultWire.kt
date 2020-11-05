package com.github.terrakok.cicerone

import java.lang.ref.WeakReference

/**
 * Interface definition for a result callback.
 */
fun interface ResultListener {
    fun onResult(data: Any)
}

internal class ResultWire {
    private val listeners = mutableMapOf<String, WeakReference<ResultListener>>()

    fun setResultListener(key: String, listener: ResultListener) {
        listeners[key] = WeakReference(listener)
    }

    fun sendResult(key: String, data: Any) {
        listeners.remove(key)?.get()?.let { listener ->
            listener.onResult(data)
        }
    }

    fun flush() {
        listeners.entries
            .filter { it.value.get() == null }
            .forEach { listeners.remove(it.key) }
    }
}