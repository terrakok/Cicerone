package ru.terrakok.cicerone.android.adapters;


public interface FragmentManagerAdapter <Fragment> {

    FragmentTransactionAdapter<Fragment> beginTransaction();

    boolean executePendingTransactions();

    void popBackStack();

    boolean popBackStackImmediate();

    boolean popBackStackImmediate(String name, int flags);

    int getBackStackEntryCount();

    BackStackEntryAdapter getBackStackEntryAt(int index);


    interface BackStackEntryAdapter {
        int getId();

        String getName();
    }
}
