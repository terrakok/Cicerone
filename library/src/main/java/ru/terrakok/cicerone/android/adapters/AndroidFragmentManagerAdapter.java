package ru.terrakok.cicerone.android.adapters;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

public class AndroidFragmentManagerAdapter implements FragmentManagerAdapter<Fragment> {
    private final FragmentManager fragmentManager;

    public AndroidFragmentManagerAdapter(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public FragmentTransactionAdapter<Fragment> beginTransaction() {
        return new AndroidFragmentTransactionAdapter(fragmentManager.beginTransaction());
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
        return new AndroidBackStackEntryAdapter(backStackEntry);
    }


    private static class AndroidBackStackEntryAdapter implements BackStackEntryAdapter {
        private final FragmentManager.BackStackEntry backStackEntry;

        public AndroidBackStackEntryAdapter(FragmentManager.BackStackEntry backStackEntry) {
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


    private static class AndroidFragmentTransactionAdapter implements FragmentTransactionAdapter<Fragment> {
        private final FragmentTransaction transaction;

        public AndroidFragmentTransactionAdapter(FragmentTransaction transaction) {
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
