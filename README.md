# Cicerone
[![jCenter](https://api.bintray.com/packages/terrakok/terramaven/cicerone/images/download.svg)](https://bintray.com/terrakok/terramaven/cicerone/_latestVersion)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)  

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Cicerone-green.svg?style=true)](https://android-arsenal.com/details/1/4700)
[![Android Weekly](https://img.shields.io/badge/Android%20Weekly-250-green.svg)](http://androidweekly.net/issues/issue-250)
[![Android Weekly](https://img.shields.io/badge/Android%20Weekly-271-green.svg)](http://androidweekly.net/issues/issue-271)  

![](https://habrastorage.org/files/644/32e/9eb/64432e9eb3664723b3ee438449dab3b0.png)

Cicerone (a guide, one who conducts sightseers) is a lightweight library that makes the navigation in an Android app easy.  
It was designed to be used with the MVP/MVVM/MVI patterns but will work great with any architecture.

## Main advantages
+ is not tied to Fragments
+ not a framework
+ short navigation calls (no builders)
+ lifecycle-safe!
+ functionality is simple to extend
+ suitable for Unit Testing

## Additional features
+ opening several screens inside single call (for example: deeplink)
+ provides `FragmentFactory` if it needed
+ `add` or `replace` strategy for opening next screen (see `router.navigateTo` last parameter)
+ implementation of parallel navigation (Instagram like)
+ predefined navigator ready for Single-Activity apps
+ predefined navigator ready for setup transition animation

## How to add
Add the dependency in your build.gradle:
```kotlin
dependencies {
    //Cicerone
    implementation("ru.terrakok.cicerone:cicerone:X.X.X")
}
```

Initialize the library (for example in your Application class):
```kotlin
class App : Application() {
    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        internal lateinit var INSTANCE: App
            private set
    }
}
```

## How it works?
<img src="https://github.com/terrakok/Cicerone/raw/develop/media/CiceroneDiagram.png" alt="drawing" width="800"/>

Presenter calls navigation method of Router.

```kotlin
class SamplePresenter(
    private val router: Router
) : Presenter<SampleView>() {

    fun onOpenNewScreen() {
        router.navigateTo(SomeScreen())
    }

    fun onBackPressed() {
        router.exit()
    }
}
```

Router converts the navigation call to the set of Commands and sends them to CommandBuffer.

CommandBuffer checks whether there are _"active"_ Navigator:
- If yes, it passes the commands to the Navigator. Navigator will process them to achive the desired transition.
- If no, then CommandBuffer saves the commands in a queue, and will apply them as soon as new _"active"_ Navigator will appear.

```kotlin
fun executeCommands(commands: Array<out Command>) {
    navigator?.applyCommands(commands) ?: pendingCommands.add(commands)
}
```

Navigator processes the navigation commands. Usually it is an anonymous class inside the Activity.
Activity provides Navigator to the CommandBuffer in _onResume_ and removes it in _onPause_.

**Attention**: Use _onResumeFragments()_ with FragmentActivity ([more info](https://developer.android.com/reference/android/support/v4/app/FragmentActivity.html#onResume()))

```kotlin
private val navigator = AppNavigator(this, R.id.container)

override fun onResumeFragments() {
    super.onResumeFragments()
    navigatorHolder.setNavigator(navigator)
}

override fun onPause() {
    navigatorHolder.removeNavigator()
    super.onPause()
}
```

## Navigation commands
This commands set will fulfill the needs of the most applications. But if you need something special - just add it!
+ Forward - Opens new screen
![](https://github.com/terrakok/Cicerone/raw/develop/media/forward_img.png)
+ Back - Rolls back the last transition
![](https://github.com/terrakok/Cicerone/raw/develop/media/back_img.png)
+ BackTo - Rolls back to the needed screen in the screens chain
![](https://github.com/terrakok/Cicerone/raw/develop/media/backTo_img.png)
+ Replace - Replaces the current screen
![](https://github.com/terrakok/Cicerone/raw/develop/media/replace_img.png)

## Predefined navigator
The library provides predefined navigator for _Fragments_ and _Activity_.
To use, just provide it with the container and _FragmentManager_.
```kotlin
private val navigator = AppNavigator(this, R.id.container)
```

Custom navigator can be useful sometimes:
```kotlin
private val navigator = object : AppNavigator(this, R.id.container) {
    override fun setupFragmentTransaction(
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment?
    ) {
        super.setupFragmentTransaction(fragmentTransaction, currentFragment, nextFragment)
        fragmentTransaction.setReorderingAllowed(true)
    }

    override fun applyCommands(commands: Array<out Command>) {
        hideKeyboard()
        super.applyCommands(commands)
    }
}
```

## Screens
Describe your screens as you like e.g. create Kotlin `object` with all application screens:
```kotlin
object Screens {
    val Main = FragmentScreen("MainFragment") { MainFragment() }
    val AddressSearch = FragmentScreen("AddressSearchFragment") { AddressSearchFragment() }
    fun Profile(userId: Long) = FragmentScreen("ProfileFragment") { ProfileFragment(userId) }
}
```

Additional you can use `FragmentFactory` for creating your screens:
```kotlin
val SomeScreen = FragmentScreen("SomeScreenId") { factory: FragmentFactory -> ... }
```

## Sample
To see how to add, initialize and use the library and predefined navigators check out 
the [GitFox (Android GitLab client)](https://gitlab.com/terrakok/gitlab-client)

![](https://github.com/terrakok/Cicerone/raw/develop/media/navigation.gif)
![](https://github.com/terrakok/Cicerone/raw/develop/media/insta_tabs.gif)
![](https://github.com/terrakok/Cicerone/raw/develop/media/animations.gif)

## Participants
+ idea and code - Konstantin Tskhovrebov (@terrakok)
+ architecture advice, documentation and publication - Vasili Chyrvon (@Jeevuz)

## License
```
MIT License

Copyright (c) 2017 Konstantin Tskhovrebov (@terrakok)
                   and Vasili Chyrvon (@Jeevuz)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
