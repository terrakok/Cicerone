package ru.terrakok.cicerone;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

import android.support.annotation.NonNull;

/**
 * Cicerone is the holder for other library components.
 * To use it, instantiate it using one of the {@link #create()} methods.
 * When you need a {@link NavigatorHolder navigation holder} or router, get it here.
 *
 * @param <T> type of router. You can use the default {@link Router} or pass your own
 *            {@link BaseRouter} implementation.
 */
public class Cicerone<T extends BaseRouter> {
    @NonNull
    private T router;

    private Cicerone(@NonNull T router) {
        this.router = router;
    }

    @NonNull
    public NavigatorHolder getNavigatorHolder() {
        return router.getCommandBuffer();
    }

    @NonNull
    public T getRouter() {
        return router;
    }

    /**
     * Creates the Cicerone instance with the default {@link Router router}
     */
    @NonNull
    public static Cicerone<Router> create() {
        return create(new Router());
    }

    /**
     * Creates the Cicerone instance with the custom router.
     * @param customRouter the custom router extending {@link BaseRouter}
     */
    @NonNull
    public static <T extends BaseRouter> Cicerone<T> create(@NonNull T customRouter) {
        return new Cicerone<>(customRouter);
    }
}
