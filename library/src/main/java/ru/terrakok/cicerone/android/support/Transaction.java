package ru.terrakok.cicerone.android.support;

class Transaction {
    final Type type;
    final String screenName;

    enum Type {
        ADD('+'), REPLACE('-');

        private final char symbol;

        Type(char symbol) {
            this.symbol = symbol;
        }
    }

    public Transaction(Type type, String screenName) {
        this.type = type;
        this.screenName = screenName;
    }

    @Override
    public String toString() {
        return screenName + type.symbol;
    }

    static Transaction fromString(String str) {
        if (str.length() < 2) {
            throw new IllegalArgumentException("Illegal transaction string: " + str);
        }

        Type type = Type.REPLACE;
        if (str.charAt(str.length() - 1) == Type.ADD.symbol) {
            type = Type.ADD;
        }
        return new Transaction(type, str.substring(0, str.length() - 1));
    }
}
