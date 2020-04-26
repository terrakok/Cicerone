package ru.terrakok.cicerone.sample.ui.main;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import moxy.MvpAppCompatFragment;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;

public abstract class BaseFragment extends MvpAppCompatFragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        FragmentActivity activity = getActivity();
        if (activity instanceof ChainHolder) {
            ((ChainHolder) activity).getChain().add(new WeakReference<Fragment>(this));
        }
    }

    @Override
    public void onDetach() {
        FragmentActivity activity = getActivity();
        if (activity instanceof ChainHolder) {
            List<WeakReference<Fragment>> chain = ((ChainHolder) activity).getChain();
            for (Iterator<WeakReference<Fragment>> it = chain.iterator(); it.hasNext();) {
                WeakReference<Fragment> fragmentReference = it.next();
                Fragment fragment = fragmentReference.get();
                if (fragment != null && fragment == this) {
                    it.remove();
                    break;
                }
            }
        }
        super.onDetach();
    }
}
