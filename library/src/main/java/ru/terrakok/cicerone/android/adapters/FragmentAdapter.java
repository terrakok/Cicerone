package ru.terrakok.cicerone.android.adapters;


public class FragmentAdapter <Fragment> {
    private final Fragment fragment;

    public FragmentAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    Fragment getFragment() {
        return fragment;
    }
}
