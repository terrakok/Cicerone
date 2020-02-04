package ru.terrakok.cicerone.android.support;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class FragmentParams {

    @NotNull
    private final Class<? extends Fragment> fragmentClass;
    @Nullable
    private final Bundle arguments;

    @NotNull
    public final Class<? extends Fragment> getFragmentClass() {
        return this.fragmentClass;
    }

    @Nullable
    public final Bundle getArguments() {
        return this.arguments;
    }

    public FragmentParams(@NotNull Class<? extends Fragment> fragmentClass, @Nullable Bundle arguments) {
        this.fragmentClass = fragmentClass;
        this.arguments = arguments;
    }

    public FragmentParams(@NotNull Class<? extends Fragment> fragmentClass) {
        this.fragmentClass = fragmentClass;
        this.arguments = null;
    }
}
