package androidx.fragment.app;

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok)
 * on 11.10.16
 */

public class FragmentTransaction {
    public FragmentTransaction setReorderingAllowed(boolean reorderingAllowed) {
        throw new RuntimeException("Stub!");
    }

    public FragmentTransaction add(int containerViewId, Fragment fragment, String tag) {
        throw new RuntimeException("Stub!");
    }

    public FragmentTransaction replace(int containerViewId, Fragment fragment, String tag) {
        throw new RuntimeException("Stub!");
    }

    public FragmentTransaction addToBackStack(String name) {
        throw new RuntimeException("Stub!");
    }

    public int commit() {
        throw new RuntimeException("Stub!");
    }
}
