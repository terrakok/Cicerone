package ru.terrakok.cicerone.android.adapters;


public interface FragmentTransactionAdapter <Fragment> {
    FragmentTransactionAdapter replace(int containerViewId, Fragment fragment);

    FragmentTransactionAdapter addToBackStack(String name);

    int commit();
}
