# Cicerone
[![jCenter](https://api.bintray.com/packages/terrakok/terramaven/cicerone/images/download.svg)](https://bintray.com/terrakok/terramaven/cicerone/_latestVersion)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)  
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Cicerone-green.svg?style=true)](https://android-arsenal.com/details/1/4700)
[![Android Weekly](https://img.shields.io/badge/Android%20Weekly-250-lightgrey.svg)](http://androidweekly.net/issues/issue-250)  
[![Telegram](https://img.shields.io/badge/Telegram-ENG-blue.svg)](https://t.me/Cicerone_ENG)
[![Telegram](https://img.shields.io/badge/Telegram-RUS-blue.svg)](https://t.me/Cicerone_RUS)  

![](https://habrastorage.org/files/644/32e/9eb/64432e9eb3664723b3ee438449dab3b0.png)

Cicerone (a guide, one who conducts sightseers) is a lightweight library that makes the navigation in an Android app easy.  
It was designed to be used with the MVP pattern (try [Moxy](https://github.com/Arello-Mobile/Moxy)), but will work great with any architecture.

[Russian version readme](https://github.com/terrakok/Cicerone/blob/develop/README_RUS.md)

## Main advantages
+ is not tied to Fragments
+ not a framework
+ short navigation calls (no builders)
+ lifecycle-safe!
+ functionality is simple to extend
+ suitable for Unit Testing

## How to add
Add the dependency in your build.gradle:
```groovy
dependencies {
    //Cicerone
    compile 'ru.terrakok.cicerone:cicerone:X.X'
}
```

Initialize the library (for example in your Application class):
```java
public class SampleApplication extends MvpApplication {
    public static SampleApplication INSTANCE;
    private Cicerone<Router> cicerone;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        initCicerone();
    }

    private void initCicerone() {
        cicerone = Cicerone.create();
    }

    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    public Router getRouter() {
        return cicerone.getRouter();
    }
}
```

## How it works?
![](https://habrastorage.org/files/4df/45d/973/4df45d9733fc4ee0a2f0be933de475b1.png)

Presenter calls navigation method of Router.

```java
public class SamplePresenter extends Presenter<SampleView> {
    private Router router;

    public SamplePresenter() {
        router = SampleApplication.INSTANCE.getRouter();
    }

    public void onBackCommandClick() {
        router.exit();
    }

    public void onForwardCommandClick() {
        router.navigateTo("Some screen");
    }
}
```

Router converts the navigation call to the set of Commands and sends them to CommandBuffer.  

CommandBuffer checks whether there are _"active"_ Navigator:  
If yes, it passes the commands to the Navigator. Navigator will process them to achive the desired transition.  
If no, then CommandBuffer saves the commands in a queue, and will apply them as soon as new _"active"_ Navigator will appear.  

```java
protected void executeCommand(Command command) {
    if (navigator != null) {
        navigator.applyCommand(command);
    } else {
        pendingCommands.add(command);
    }
}
```

Navigator processes the navigation commands. Usually it is an anonymous class inside the Activity.  
Activity provides Navigator to the CommandBuffer in _onResume_ and removes it in _onPause_.  

**Attention**: Use _onResumeFragments()_ with FragmentActivity ([more info](https://developer.android.com/reference/android/support/v4/app/FragmentActivity.html#onResume()))

```java
@Override
protected void onResume() {
    super.onResume();
    SampleApplication.INSTANCE.getNavigatorHolder().setNavigator(navigator);
}

@Override
protected void onPause() {
    super.onPause();
    SampleApplication.INSTANCE.getNavigatorHolder().removeNavigator();
}

private Navigator navigator = new Navigator() {
    @Override
    public void applyCommand(Command command) {
        //implement commands logic
    }
};
```

## Navigation commands
This commands set will fulfill the needs of the most applications. But if you need something special - just add it!
+ Forward - Opens new screen  
![](https://habrastorage.org/files/862/77e/b20/86277eb20b574dae8307ac4f64b0f090.png)
+ Back - Rolls back the last transition  
![](https://habrastorage.org/files/059/b63/2d3/059b632d3a7c4515a534b9e5e881c8f0.png)
+ BackTo - Rolls back to the needed screen in the screens chain  
![](https://habrastorage.org/files/a45/4f4/c34/a454f4c340764632ad0669014ad5550d.png)
+ Replace - Replaces the current screen  
![](https://habrastorage.org/files/4ae/95c/fee/4ae95cfee4c04f038ad17d358ab08d07.png)
+ SystemMessage - Shows system message (Alert, Toast, Snack, etc.)  
![](https://habrastorage.org/files/6e7/1a6/4ed/6e71a64edec04079bf33faa7ab39606f.png)

## Predefined navigators
The library provides predefined navigators for _Fragments_ to use inside _Activity_.    
To use, just provide it with the container and _FragmentManager_ and override few simple methods.  
```java
private Navigator navigator = new SupportFragmentNavigator(
                              getSupportFragmentManager(), R.id.main_container) {
    @Override
    protected Fragment createFragment(String screenKey, Object data) {
        return SampleFragment.getNewInstance((int) data);
    }

    @Override
    protected void showSystemMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void exit() {
        finish();
    }
};
```
## Sample
To see how to add, initialize and use the library and predefined navigators check out the sample.

![](https://habrastorage.org/files/16d/2ee/6e3/16d2ee6e33a0428eb4f0dcab8ce6b294.gif)

![](https://hsto.org/files/867/638/c33/867638c338704489b3107a6d7cb28c2d.gif)

## Participants
+ idea and code - Konstantin Tskhovrebov (@terrakok)
+ architecture advice, documentation and publication - Vasili Chyrvon (@Jeevuz)

## License
```
The MIT License (MIT)

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
