# Cicerone
[![Bintray](https://img.shields.io/bintray/v/terrakok/terramaven/cicerone.svg)](https://bintray.com/terrakok/terramaven/cicerone)
[![License](https://img.shields.io/badge/license-APACHE2-blue.svg)](http://choosealicense.com/licenses/apache-2.0/)

Cicerone (a guide, one who conducts sightseers) is a lightweight library that makes the navigation in an Android app easy.  
It was designed to be used with the MVP pattern (try [Moxy](https://github.com/Arello-Mobile/Moxy)), but will work great with any architecture.

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
    compile 'ru.terrakok.cicerone:cicerone:1.1'
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

## Participants
+ idea and code - Konstantin Tskhovrebov (@terrakok)
+ architecture advice, documentation and publication - Vasili Chyrvon (@Jeevuz)

## License

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
