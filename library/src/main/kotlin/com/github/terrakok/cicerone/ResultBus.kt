package com.github.terrakok.cicerone

import java.lang.ref.WeakReference

internal class ResultBus {
    private val listeners = mutableMapOf<String, WeakReference<(Any) -> Unit>>()

    fun setResultListener(key: String, listener: (data: Any) -> Unit) {
        listeners[key] = WeakReference(listener)
    }

    fun sendResult(key: String, data: Any) {
        listeners.remove(key)?.get()?.let { listener ->
            listener(data)
        }
    }

    fun flush() {
        listeners.entries
            .filter { it.value.get() == null }
            .forEach { listeners.remove(it.key) }
    }
}