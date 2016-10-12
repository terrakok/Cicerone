package ru.terrakok.cicerone;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

public class Cicerone<T extends BaseRouter> {
    private T router;

    private Cicerone(T router) {
        this.router = router;
    }

    public NavigatorHolder getNavigatorHolder() {
        return router.getCommandBuffer();
    }

    public T getRouter() {
        return router;
    }

    public static Cicerone<Router> create() {
        return create(new Router());
    }

    public static <T extends BaseRouter> Cicerone<T> create(T customRouter) {
        return new Cicerone<>(customRouter);
    }
}
