package ru.terrakok.cicerone.sample.mvp.main;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

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
        router.navigateTo(Screens.SAMPLE_SCREEN + (screenNumber + 1), screenNumber + 1);
    }

    public void onReplaceCommandClick() {
        router.replaceScreen(Screens.SAMPLE_SCREEN + (screenNumber + 1), screenNumber + 1);
    }

    public void onNewChainCommandClick() {
        router.newScreenChain(Screens.SAMPLE_SCREEN + (screenNumber + 1), screenNumber + 1);
    }

    public void onNewRootCommandClick() {
        router.newRootScreen(Screens.SAMPLE_SCREEN + (screenNumber + 1), screenNumber + 1);
    }

    public void onBackWithMessageCommandClick() {
        router.exitWithMessage("Exit from 'Screen " + screenNumber + "'");
    }

    public void onForwardWithDelayCommandClick() {
        if (future != null) future.cancel(true);
        future = executorService.schedule(new Runnable() {
            @Override
            public void run() {
                //WARNING! Navigation must be only in UI thread. this method works only for sample :)
                router.navigateTo(Screens.SAMPLE_SCREEN + (screenNumber + 1), screenNumber + 1);
            }
        }, 5, TimeUnit.SECONDS);
    }

    public void onBackToCommandClick() {
        router.backTo(Screens.SAMPLE_SCREEN + 3);
    }
}
