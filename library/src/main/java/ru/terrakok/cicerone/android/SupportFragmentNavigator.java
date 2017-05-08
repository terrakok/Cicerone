package ru.terrakok.cicerone.android;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.adapters.SupportFragmentManagerAdapter;
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.BackTo;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

/**
 * {@link Navigator} implementation based on the support fragments.
 * <p>
 * {@link BackTo} navigation command will return to the root if
 * needed screen isn't found in the screens chain.
 * To change this behavior override {@link #backToUnexisting()} method.
 * </p>
 * <p>
 * {@link Back} command will call {@link #exit()} method if current screen is the root.
 * </p>
 */
public abstract class SupportFragmentNavigator extends FragmentNavigatorBase<Fragment> {
    /**
     * Creates SupportFragmentNavigator.
     *
     * @param fragmentManager support fragment manager
     * @param containerId     id of the fragments container layout
     */
    public SupportFragmentNavigator(FragmentManager fragmentManager, int containerId) {
        super(new SupportFragmentManagerAdapter(fragmentManager), containerId);
    }

}