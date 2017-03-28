package ru.terrakok.cicerone.commands;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

import android.support.annotation.AnimRes;

/**
 * Replaces the current screen.
 */
public class Replace extends AnimationCommand {
	private String screenKey;
	private Object transitionData;

	/**
	 * Creates a {@link Replace} navigation command.
	 *
	 * @param screenKey screen key
	 * @param transitionData initial data
	 */
	public Replace(String screenKey, Object transitionData) {
		this.screenKey = screenKey;
		this.transitionData = transitionData;
	}

	/**
	 * Creates a {@link Replace} navigation command.
	 *
	 * @param screenKey screen key
	 * @param transitionData initial data
	 */
	public Replace(String screenKey, Object transitionData, @AnimRes int enterAnim, @AnimRes int exitAnim) {
		super(enterAnim, exitAnim);
		this.screenKey = screenKey;
		this.transitionData = transitionData;
	}

	/**
	 * Creates a {@link Replace} navigation command.
	 *
	 * @param screenKey screen key
	 * @param transitionData initial data
	 */
	public Replace(String screenKey, Object transitionData, @AnimRes int enterAnim, @AnimRes int exitAnim, @AnimRes int popEnterAnim, @AnimRes int popExitAnim) {
		super(enterAnim, exitAnim, popEnterAnim, popExitAnim);
		this.screenKey = screenKey;
		this.transitionData = transitionData;
	}

	public String getScreenKey() {
		return screenKey;
	}

	public Object getTransitionData() {
		return transitionData;
	}
}
