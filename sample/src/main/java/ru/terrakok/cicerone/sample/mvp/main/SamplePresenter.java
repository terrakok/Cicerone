package ru.terrakok.cicerone.sample.mvp.main;

import android.os.Handler;
import android.os.Looper;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.github.terrakok.cicerone.Router;
import com.github.terrakok.cicerone.Screen;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

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
        router.navigateTo(Screens.sampleScreen(screenNumber + 1), true);
    }

    public void onReplaceCommandClick() {
        router.replaceScreen(Screens.sampleScreen(screenNumber + 1));
    }

    public void onNewChainCommandClick() {
        router.newChain(new Screen[]{
                Screens.sampleScreen(screenNumber + 1),
                Screens.sampleScreen(screenNumber + 2),
                Screens.sampleScreen(screenNumber + 3)
        }, true);
    }

    public void onFinishChainCommandClick() {
        router.finishChain();
    }

    public void onNewRootCommandClick() {
        router.newRootScreen(Screens.sampleScreen(screenNumber + 1));
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
                                router.navigateTo(Screens.sampleScreen(screenNumber + 1), true);
                            }
                        }
                );
            }
        }, 5, TimeUnit.SECONDS);
    }

    public void onBackToCommandClick() {
        router.backTo(Screens.sampleScreen(3));
    }
}
