package ru.terrakok.cicerone.sample.mvp.bottom.forward;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.github.terrakok.cicerone.Router;

import ru.terrakok.cicerone.sample.Screens;

/**
 * Created by terrakok 26.11.16
 */

@InjectViewState
public class ForwardPresenter extends MvpPresenter<ForwardView> {
    private String container;
    private Router router;
    private int number;

    public ForwardPresenter(String container, Router router, int number) {
        this.container = container;
        this.router = router;
        this.number = number;

        getViewState().setChainText(createChain(number));
    }

    private String createChain(int number) {
        String chain = "[0]";

        for (int i = 0; i < number; i++) {
            chain += "➔" + (i + 1);
        }

        return chain;
    }

    public void onForwardPressed() {
        router.navigateTo(Screens.forwardScreen(container, number + 1), true);
    }

    public void onGithubPressed() {
        router.navigateTo(Screens.githubScreen(), true);
    }

    public void onBackPressed() {
        router.exit();
    }
}
