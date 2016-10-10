package android.support.v4.app;

/**
 * Created by terrakok 10.10.16
 */
public class FragmentManager {

    public FragmentTransaction beginTransaction() {
        throw new RuntimeException("Stub!");
    }

    public boolean executePendingTransactions() {
        throw new RuntimeException("Stub!");
    }

    public void popBackStack() {
        throw new RuntimeException("Stub!");
    }

    public boolean popBackStackImmediate() {
        throw new RuntimeException("Stub!");
    }

    public boolean popBackStackImmediate(String name, int flags) {
        throw new RuntimeException("Stub!");
    }

    public int getBackStackEntryCount() {
        throw new RuntimeException("Stub!");
    }

    public BackStackEntry getBackStackEntryAt(int index) {
        throw new RuntimeException("Stub!");
    }


    public interface BackStackEntry {
        int getId();

        String getName();
    }
}
