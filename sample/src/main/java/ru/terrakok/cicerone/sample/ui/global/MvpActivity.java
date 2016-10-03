package ru.terrakok.cicerone.sample.ui.global;

/*
 * Created by Konstantin Tckhovrebov (aka terrakok) on 23.05.16 14:39.
 * Copyright (c) 2016 MobileUp.ru.  All rights reserved.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.arellomobile.mvp.MvpDelegate;

public class MvpActivity extends AppCompatActivity {
    private MvpDelegate<? extends MvpActivity> mMvpDelegate;

    public MvpActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getMvpDelegate().onCreate(savedInstanceState);
    }

    protected void onDestroy() {
        super.onDestroy();
        this.getMvpDelegate().onDetach();
        if (this.isFinishing()) {
            this.getMvpDelegate().onDestroy();
        }

    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.getMvpDelegate().onSaveInstanceState(outState);
    }

    protected void onStart() {
        super.onStart();
        this.getMvpDelegate().onAttach();
    }

    public MvpDelegate getMvpDelegate() {
        if (this.mMvpDelegate == null) {
            this.mMvpDelegate = new MvpDelegate<>(this);
        }

        return this.mMvpDelegate;
    }
}

