package ru.terrakok.cicerone

/**
 * Cicerone is the holder for other library components.
 * To use it, instantiate it using one of the [create] methods.
 * When you need a [navigation holder][NavigatorHolder] or router, get it here.
 *
 * @param T type of router. You can use the default [Router] or pass your own
 * [BaseRouter] implementation.
 * @author Konstantin Tskhovrebov (aka terrakok) on 11.10.16.
 */
class Cicerone<out T : BaseRouter> private constructor(val router: T) {

    val navigatorHolder: NavigatorHolder
        get() = router.commandBuffer

    companion object {
        /**
         * Creates the Cicerone instance with the default [router][Router].
         */
        @JvmStatic
        fun create(): Cicerone<Router> {
            return create(Router())
        }

        /**
         * Creates the Cicerone instance with the custom router.
         *
         * @param customRouter the custom router extending [BaseRouter]
         */
        @JvmStatic
        fun <T : BaseRouter> create(customRouter: T): Cicerone<T> {
            return Cicerone(customRouter)
        }
    }
}
