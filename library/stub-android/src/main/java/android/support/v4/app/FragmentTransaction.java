package android.support.v4.app;

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

    public FragmentTransaction setCustomAnimations(int enter, int exit) {
        throw new RuntimeException("Stub!");
    }

    public FragmentTransaction setCustomAnimations(int enter, int exit, int popEnter, int popExit) {
        throw new RuntimeException("Stub!");
    }

    public int commit() {
        throw new RuntimeException("Stub!");
    }
}
