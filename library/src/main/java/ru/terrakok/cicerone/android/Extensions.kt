package ru.terrakok.cicerone.android

import android.app.FragmentManager
import android.app.FragmentTransaction

internal inline fun FragmentManager.makeTransaction(transaction: FragmentTransaction.() -> Unit) {
    this.beginTransaction().apply(transaction).commit()
}
