package ru.terrakok.cicerone.android.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class SupportFragmentManagerAdapter implements FragmentManagerAdapter<Fragment> {
    private final FragmentManager fragmentManager;

    public SupportFragmentManagerAdapter(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public FragmentTransactionAdapter<Fragment> beginTransaction() {
        return new SupportFragmentTransactionAdapter(fragmentManager.beginTransaction());
    }

    @Override
    public boolean executePendingTransactions() {
        return fragmentManager.executePendingTransactions();
    }

    @Override
    public void popBackStack() {
        fragmentManager.popBackStack();
    }

    @Override
    public boolean popBackStackImmediate() {
        return fragmentManager.popBackStackImmediate();
    }

    @Override
    public boolean popBackStackImmediate(String name, int flags) {
        return fragmentManager.popBackStackImmediate(name, flags);
    }

    @Override
    public int getBackStackEntryCount() {
        return fragmentManager.getBackStackEntryCount();
    }

    @Override
    public BackStackEntryAdapter getBackStackEntryAt(int index) {
        FragmentManager.BackStackEntry backStackEntry = fragmentManager.getBackStackEntryAt(index);
        return new SupportBackStackEntryAdapter(backStackEntry);
    }

    private static class SupportBackStackEntryAdapter implements BackStackEntryAdapter {
        private final FragmentManager.BackStackEntry backStackEntry;

        public SupportBackStackEntryAdapter(FragmentManager.BackStackEntry backStackEntry) {
            this.backStackEntry = backStackEntry;
        }

        @Override
        public int getId() {
            return backStackEntry.getId();
        }

        @Override
        public String getName() {
            return backStackEntry.getName();
        }
    }

    private static class SupportFragmentTransactionAdapter implements FragmentTransactionAdapter<Fragment> {

        private final FragmentTransaction transaction;

        public SupportFragmentTransactionAdapter(FragmentTransaction transaction) {
            this.transaction = transaction;
        }

        @Override
        public FragmentTransactionAdapter replace(int containerViewId, Fragment fragment) {
            transaction.replace(containerViewId, fragment);
            return this;
        }

        @Override
        public FragmentTransactionAdapter addToBackStack(String name) {
            transaction.addToBackStack(name);
            return this;
        }

        @Override
        public int commit() {
            return transaction.commit();
        }
    }
}
