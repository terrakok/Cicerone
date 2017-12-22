package ru.terrakok.cicerone.android

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

internal inline fun FragmentManager.makeTransaction(transaction: FragmentTransaction.() -> Unit) {
    this.beginTransaction().apply(transaction).commit()
}
