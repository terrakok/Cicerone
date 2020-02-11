/*
 * Created by Konstantin Tskhovrebov (aka @terrakok)
 */

package ru.terrakok.cicerone;

import org.jetbrains.annotations.NotNull;

/**
 * Cicerone is the holder for other library components.
 * To use it, instantiate it using one of the {@link #create()} methods.
 * When you need a {@link NavigatorHolder navigation holder} or router, get it here.
 *
 * @param <T> type of router. You can use the default {@link Router} or pass your own
 *            {@link BaseRouter} implementation.
 */
public class Cicerone<T extends BaseRouter> {
    private T router;

    private Cicerone(@NotNull T router) {
        this.router = router;
    }

    @NotNull
    public NavigatorHolder getNavigatorHolder() {
        return router.getCommandBuffer();
    }

    @NotNull
    public T getRouter() {
        return router;
    }

    /**
     * Creates the Cicerone instance with the default {@link Router router}
     */
    @NotNull
    public static Cicerone<Router> create() {
        return create(new Router());
    }

    /**
     * Creates the Cicerone instance with the custom router.
     * @param customRouter the custom router extending {@link BaseRouter}
     */
    @NotNull
    public static <T extends BaseRouter> Cicerone<T> create(@NotNull T customRouter) {
        return new Cicerone<>(customRouter);
    }
}
