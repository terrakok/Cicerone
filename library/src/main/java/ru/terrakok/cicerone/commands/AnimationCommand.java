package ru.terrakok.cicerone.commands;

import android.support.annotation.AnimRes;

public class AnimationCommand implements Command {
    @AnimRes
    protected final Integer enterAnim;
    @AnimRes
    protected final Integer exitAnim;
    @AnimRes
    protected final Integer popEnterAnim;
    @AnimRes
    protected final Integer popExitAnim;

    public AnimationCommand() {
        this.enterAnim = this.exitAnim = this.popEnterAnim = this.popExitAnim = null;
    }

    public AnimationCommand(@AnimRes int enterAnim, @AnimRes int exitAnim) {
        this.enterAnim = enterAnim;
        this.exitAnim = exitAnim;
        this.popEnterAnim = this.popExitAnim = null;
    }

    public AnimationCommand(@AnimRes int enterAnim, @AnimRes int exitAnim, @AnimRes int popEnterAnim, @AnimRes int popExitAnim) {
        this.enterAnim = enterAnim;
        this.exitAnim = exitAnim;
        this.popEnterAnim = popEnterAnim;
        this.popExitAnim = popExitAnim;
    }

    public Integer getEnterAnim() {
        return enterAnim;
    }

    public Integer getExitAnim() {
        return exitAnim;
    }

    public Integer getPopEnterAnim() {
        return popEnterAnim;
    }

    public Integer getPopExitAnim() {
        return popExitAnim;
    }

    public boolean hasAnimations() {
        return enterAnim != null && exitAnim != null;
    }

    public boolean hasPopAnimations() {
        return hasAnimations() && popExitAnim != null && popEnterAnim != null;
    }

}
