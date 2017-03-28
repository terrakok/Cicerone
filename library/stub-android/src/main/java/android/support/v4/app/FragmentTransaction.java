package android.support.v4.app;

import android.support.annotation.AnimRes;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

public class FragmentTransaction {
	public FragmentTransaction replace(int containerViewId, Fragment fragment) {
		throw new RuntimeException("Stub!");
	}

	public FragmentTransaction addToBackStack(String name) {
		throw new RuntimeException("Stub!");
	}

	public FragmentTransaction setCustomAnimations(@AnimRes int enter, @AnimRes int exit) {
		throw new RuntimeException("Stub!");

	}

	public FragmentTransaction setCustomAnimations(@AnimRes int enter, @AnimRes int exit, @AnimRes int popEnter, @AnimRes int popExit) {
		throw new RuntimeException("Stub!");
	}

	public int commit() {
		throw new RuntimeException("Stub!");
	}
}
