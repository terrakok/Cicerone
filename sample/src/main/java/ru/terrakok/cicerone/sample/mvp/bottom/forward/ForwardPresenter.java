package ru.terrakok.cicerone.sample.mvp.bottom.forward;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;
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
        StringBuilder chain = new StringBuilder("[0]");

        for (int i = 0; i < number; i++) {
            chain.append("➔").append(i + 1);
        }

        return chain.toString();
    }

    public void onForwardPressed() {
        router.navigateTo(new Screens.ForwardScreen(container, number + 1));
    }

    public void onGithubPressed() {
        router.navigateTo(new Screens.GithubScreen());
    }

    public void onBackPressed() {
        router.exit();
    }
}
