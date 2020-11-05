package com.github.terrakok.cicerone.androidx

data class TransactionInfo(
    val screenKey: String,
    val type: Type
) {
    enum class Type(val symbol: Char) {
        ADD('+'),
        REPLACE('-');
    }

    override fun toString() = screenKey + type.symbol

    companion object {
        @JvmStatic
        fun fromString(str: String) = TransactionInfo(
            str.dropLast(1),
            if (str.last() == Type.ADD.symbol) Type.ADD else Type.REPLACE
        )
    }
}