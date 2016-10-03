package ru.terrakok.cicerone.sample.ui.global;

/*
 * Created by Konstantin Tckhovrebov (aka terrakok) on 23.05.16 14:39.
 * Copyright (c) 2016 MobileUp.ru.  All rights reserved.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.arellomobile.mvp.MvpDelegate;

public class MvpFragment extends Fragment {
    private MvpDelegate<? extends MvpFragment> mMvpDelegate;

    public MvpFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getMvpDelegate().onCreate(savedInstanceState);
    }

    public void onStart() {
        super.onStart();
        this.getMvpDelegate().onAttach();
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.getMvpDelegate().onDetach();
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.isRemoving() || getActivity().isFinishing()) {
            this.getMvpDelegate().onDestroy();
        }

    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.getMvpDelegate().onSaveInstanceState(outState);
    }

    public MvpDelegate getMvpDelegate() {
        if (this.mMvpDelegate == null) {
            this.mMvpDelegate = new MvpDelegate<>(this);
        }

        return this.mMvpDelegate;
    }
}