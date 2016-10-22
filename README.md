# Cicerone

Cicerone (a guide who gives information to sightseers) is a lightweight library that makes the navigation in an Android app easy.  
It designed for using with MVP architecture (try [Moxy](https://github.com/Arello-Mobile/Moxy)), but it fits to work in other ways.

## Main advantages
+ not tied to Fragments
+ not framework
+ short navigation calls (no builders)
+ lifecycle-safely!
+ functional is simple to extent
+ adapted for Unit Testing

## How to connect?
Add the following lines to build.gradle:
```groovy
repositories {
    maven {
        url 'https://dl.bintray.com/terrakok/terramaven/'
    }
}

dependencies {
    //Cicerone
    compile 'ru.terrakok.cicerone:cicerone:1.0'
}
```
And initialise library for example with application:
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

Router converts the navigation calls to Comand sets and sends them to CommandBuffer.  
Command Buffer checks whether there _"active"_ Navigator.  
If yes, it caused the necessary commands for the requested transfer.  
If not, the command added to the queue, which will be applied as soon as _"active"_ Navigator.  

```java
protected void executeCommand(Command command) {
    if (navigator != null) {
        navigator.applyCommand(command);
    } else {
        pendingCommands.add(command);
    }
}
```

Navigator - implements the navigation commands, e.g. anonymous class inside the Activity.  
Activity provides Navigator for CommandBuffer in _onResume_ and remove in _onPause_  

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
        //implements commands logic
    }
};
```

## Navigation commands
These command set will fulfill needs of the most application. But if you require more - supply your own!
+ Forward - Opens new screen  
![](https://habrastorage.org/files/862/77e/b20/86277eb20b574dae8307ac4f64b0f090.png)
+ Back - Rolls back the last transition from the screens chain  
![](https://habrastorage.org/files/059/b63/2d3/059b632d3a7c4515a534b9e5e881c8f0.png)
+ BackTo - Rolls back to the needed screen from the screens chain  
![](https://habrastorage.org/files/a45/4f4/c34/a454f4c340764632ad0669014ad5550d.png)
+ Replace - Replaces the current screen  
![](https://habrastorage.org/files/4ae/95c/fee/4ae95cfee4c04f038ad17d358ab08d07.png)
+ SystemMessage - Shows system message (Alert, Toast, Snack etc)  
![](https://habrastorage.org/files/6e7/1a6/4ed/6e71a64edec04079bf33faa7ab39606f.png)

## Ready navigators
The library provides ready navigators for _Activity_.  
To use them, just give the container and pass _FragmentManager_.  
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
Library usage examples, ready navigators and more can be found in the sample application.

![](https://habrastorage.org/files/16d/2ee/6e3/16d2ee6e33a0428eb4f0dcab8ce6b294.gif)

## Participants
+ idea and realization - Konstantin Tckhovrebov (@terrakok)
+ architectural advice, documentation and publication - Vasily Chirvon (@Jeevuz)

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
