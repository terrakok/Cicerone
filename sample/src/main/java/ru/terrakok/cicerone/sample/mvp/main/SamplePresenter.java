package ru.terrakok.cicerone.sample.mvp.main;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.sample.Screens;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

@InjectViewState
public class SamplePresenter extends MvpPresenter<SampleView> {
    private Router router;
    private int screenNumber;
    private ScheduledExecutorService executorService;
    private ScheduledFuture<?> future;

    public SamplePresenter(Router router, int screenNumber) {
        this.router = router;
        this.screenNumber = screenNumber;
        executorService = Executors.newSingleThreadScheduledExecutor();

        getViewState().setTitle("Screen " + screenNumber);
    }

    public void onBackCommandClick() {
        router.exit();
    }

    public void onForwardCommandClick() {
        router.navigateTo(new Screens.SampleScreen(screenNumber + 1));
    }

    public void onReplaceCommandClick() {
        router.replaceScreen(new Screens.SampleScreen(screenNumber + 1));
    }

    public void onNewChainCommandClick() {
        router.newChain(
                new Screens.SampleScreen(screenNumber + 1),
                new Screens.SampleScreen(screenNumber + 2),
                new Screens.SampleScreen(screenNumber + 3)
        );
    }

    public void onFinishChainCommandClick() {
        router.finishChain();
    }

    public void onNewRootCommandClick() {
        router.newRootScreen(new Screens.SampleScreen(screenNumber + 1));
    }

    public void onForwardWithDelayCommandClick() {
        if (future != null) future.cancel(true);
        future = executorService.schedule(new Runnable() {
            @Override
            public void run() {
                //WARNING! Navigation must be only in UI thread.
                new Handler(Looper.getMainLooper()).post(
                        new Runnable() {
                            @Override
                            public void run() {
                                router.navigateTo(new Screens.SampleScreen(screenNumber + 1));
                            }
                        }
                );
            }
        }, 5, TimeUnit.SECONDS);
    }

    public void onBackToCommandClick() {
        router.backTo(new Screens.SampleScreen(3));
    }
}
