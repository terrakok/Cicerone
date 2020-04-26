package ru.terrakok.cicerone

class Cicerone<T : BaseRouter> private constructor(private var router: T) {

    fun getNavigatorHolder(): NavigatorHolder = router.commandBuffer

    fun getRouter() = router

    fun create() = create(Router())

    fun <T : BaseRouter> create(customRouter: T) = Cicerone(customRouter)
}