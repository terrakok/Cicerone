# Cicerone
[![Maven Central](https://img.shields.io/maven-central/v/com.github.terrakok/cicerone)](https://repo1.maven.org/maven2/com/github/terrakok/cicerone/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)  

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Cicerone-green.svg?style=true)](https://android-arsenal.com/details/1/4700)
[![Android Weekly](https://img.shields.io/badge/Android%20Weekly-250-green.svg)](http://androidweekly.net/issues/issue-250)
[![Android Weekly](https://img.shields.io/badge/Android%20Weekly-271-green.svg)](http://androidweekly.net/issues/issue-271)  

<table>
    <tr>
        <td>
            <img src="https://github.com/terrakok/Cicerone/raw/master/media/navigation.gif" width="256"/>
        </td>
        <td>
            <img src="https://github.com/terrakok/Cicerone/raw/master/media/insta_tabs.gif" width="256"/>
        </td>
        <td>
            <img src="https://github.com/terrakok/Cicerone/raw/master/media/animations.gif" width="256"/>
        </td>
    </tr>
    <tr>
        <td>
            Power navigation
        </td>
        <td>
            Multibackstack
        </td>
        <td>
            Result listeners
        </td>
    </tr>
</table>

Cicerone (means - a guide, one who conducts sightseers) is a lightweight library that makes the navigation in an Android app easy.  
It was designed to be used with the MVP/MVVM/MVI patterns but will work great with any architecture.

## Main advantages
+ is not tied to Fragments
+ not a framework (very lightweight)
+ short navigation calls (no builders)
+ static typed checks for screen parameters!
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
    implementation("com.github.terrakok:cicerone:X.X.X")
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
<img src="https://github.com/terrakok/Cicerone/blob/master/media/CiceroneDiagram.png" alt="CiceroneDiagram.png" width="800"/>

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
![](https://github.com/terrakok/Cicerone/raw/master/media/forward_img.png)
+ Back - Rolls back the last transition
![](https://github.com/terrakok/Cicerone/raw/master/media/back_img.png)
+ BackTo - Rolls back to the needed screen in the screens chain
![](https://github.com/terrakok/Cicerone/raw/master/media/backTo_img.png)
+ Replace - Replaces the current screen
![](https://github.com/terrakok/Cicerone/raw/master/media/replace_img.png)

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
        screen: FragmentScreen,
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment
    ) {
        //setup your animation
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
    fun Main() = FragmentScreen { MainFragment() }
    fun AddressSearch() = FragmentScreen { AddressSearchFragment() }
    fun Profile(userId: Long) = FragmentScreen("Profile_$userId") { ProfileFragment(userId) }
    fun Browser(url: String) = ActivityScreen { Intent(Intent.ACTION_VIEW, Uri.parse(url))  }
}
```

Additional you can use `FragmentFactory` for creating your screens:
```kotlin
fun SomeScreen() = FragmentScreen { factory: FragmentFactory -> ... }
```

## Screen parameters and result listener
```kotlin
//you have to specify screen parameters via new FragmentScreen creation
fun SelectPhoto(resultKey: String) = FragmentScreen {
    SelectPhotoFragment.getNewInstance(resultKey)
}
```

```kotlin
//listen result
fun onSelectPhotoClicked() {
    router.setResultListener(RESULT_KEY) { data ->
        view.showPhoto(data as Bitmap)
    }
    router.navigateTo(SelectPhoto(RESULT_KEY))
}

//send result
fun onPhotoClick(photo: Bitmap) {
    router.sendResult(resultKey, photoRes)
    router.exit()
}
```

## Sample
To see how to add, initialize and use the library and predefined navigators see **sample project**  
(thank you [@Javernaut](https://github.com/Javernaut) for support new library version and migrate sample project to Kotlin!)

For more complex use case check out the [GitFox (Android GitLab client)](https://gitlab.com/terrakok/gitlab-client)

## Applications with Cicerone inside
<a href="https://play.google.com/store/apps/details?id=ru.foodfox.client"><img src="https://play-lh.googleusercontent.com/gWYedIqy8QujCQOn0kzEIBEkGLBSpuKvFm-fMcfkWnJ1Oirtv847xAE4OyhAaohdcp5V=s360" width="64" /> Яндекс.Еда — доставка еды/продуктов. Food delivery</a><br>
<a href="https://play.google.com/store/apps/details?id=com.kms.me"><img src="https://play-lh.googleusercontent.com/IBzu0tlHd_amw2HbjBLOZiCfK-0tn0CnwkMdOd1toP23rdHUV-i7L2ViNKgIg687=s360" width="64" /> Kaspersky Internet Security</a><br>
<a href="https://play.google.com/store/apps/details?id=com.deliveryclub"><img src="https://play-lh.googleusercontent.com/m6-gFunvj7aQD5fdv8EdJZBN5M4REIobTaPZPYS0K5Td7CNYnazN7fOKiPwwaY3hJw=s360" width="64" /> Delivery Club – Доставка еды и продуктов</a><br>
<a href="https://play.google.com/store/apps/details?id=ru.hh.android"><img src="https://play-lh.googleusercontent.com/YpAV7Q-ZJhI5tzFk_wEX-7-x2BydtnCtFTVUrmq0zAO6jLCLA4nNcfem3p_Pyowg9w=s360" width="64" /> Поиск работы на hh. Вакансии рядом с домом</a><br>
<a href="https://play.google.com/store/apps/details?id=com.foodient.whisk"><img src="https://play-lh.googleusercontent.com/eKotZjJcZOU2_L9t2l34EEY7aGl5zhvKVuEbF0Kc4MRs_pAC2SJgOnWMkMTFjR_e9EY=s360" width="64" /> Whisk: Recipe Saver, Meal Planner & Grocery List</a><br>
<a href="https://play.google.com/store/apps/details?id=kz.beeline.odp"><img src="https://play-lh.googleusercontent.com/hzgjpQQpy6Z-Byye0aVKSv9P7h8yx58i6pVkQtiM6jB99iWFXjYfKeaPqJ3wm6Rtb38=s360" width="64" /> Мой Beeline (Казахстан)</a><br>
<a href="https://play.google.com/store/apps/details?id=com.mercuryo.app"><img src="https://play-lh.googleusercontent.com/FKulXdc15r5PWX6hTZi2i3iaJjcQHwd9xParp6YPiQ2KiBqza7jwEt_b_tqLwXpyEHg=s360" width="64" /> Mercuryo Bitcoin Cryptowallet</a><br>
<a href="https://play.google.com/store/apps/details?id=com.warefly.checkscan"><img src="https://play-lh.googleusercontent.com/2c2uuiSl2vwGgp-vdI-VArQEMdSSXk1neUK5A-Udc0WANPcvp5kBJFEugrFiXnxUc7k=s360" width="64" /> ЧекСкан - кэшбэк за чеки, цены и акции в магазинах</a><br>
<a href="https://github.com/eduard1abdulmanov123/News"><img src="https://raw.githubusercontent.com/eduard1abdulmanov123/News/dev/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png" width="64" /> RSS Reader для Вести.Ru</a><br>
<a href="https://play.google.com/store/apps/details?id=com.epam.connect.android"><img src="https://play-lh.googleusercontent.com/aN7R6BiR7yt7b3oEoBI30pVwzsdzaWe3TWpw8c9igqoOj79Pm2xVh4_C4qwjSKwjVio=s360" width="64" /> EPAM Connect</a><br>
<a href="https://play.google.com/store/apps/details?id=org.consumerreports.ratings"><img src="https://play-lh.googleusercontent.com/dEdOwZOjXAdamytxY1TgY8LS-Hc9FKCcit5HP1RyaKqRAWjDJEyFSQS1XlqQPpeY5UI=s360" width="64" /> Consumer Reports: Product Reviews & Ratings</a><br>
<a href="https://play.google.com/store/apps/details?id=ru.zakaz.android"><img src="https://play-lh.googleusercontent.com/jj18yK2dB2MHZ_QdO21aXyznGXteIF2q4mgxY4ubLhFv9gwZqHVDeu1i2FmanS-0Furm=s360" width="64" /> Zakaz.ru</a><br>

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
