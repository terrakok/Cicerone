# Cicerone

Cicerone (_"чи-че-ро́-не"_ - устар. гид) - легкая библиотека для простой реализации навигации в андроид приложении.  
Разработана для использования в MVP архитектуре (попробуйте [Moxy](https://github.com/Arello-Mobile/Moxy)), но легко встраивается в любые решения.

## Основные преимущества
+ работает на стандартном фреймворке
+ короткие вызовы навигации (никаких билдеров)
+ lifecycle-безопасна!
+ простое расширение функционала
+ приспособлена для Unit тестов

## Как это работает?
![](https://habrastorage.org/files/e3a/461/28c/e3a46128c301418b8804bde0363ac2fe.png)

Presenter через Router вызывает метод навигации.

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

Cicerone проверяет есть ли _"активный"_ Navigator.  
Если да, то на нем вызываются необходимые команды для осуществления запрошенного перехода.  
Если нет, то команды добавляются в очередь, которая будет применена, как только появится _"активный"_ Navigator.

```java
protected void executeCommand(Command command) {
    if (navigator != null) {
        navigator.applyCommand(command);
    } else {
        pendingCommands.add(command);
    }
}
```

Navigator (например) - анонимный класс внутри Activity, который реализует всего несколько команд.  
Activity предоставляет свой Navigator для Cicerone в методе _onResume_ и очищает в _onPause_

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

SampleApplication не содержит никакой магии :-)

```java
public class SampleApplication extends Application {
    public static SampleApplication INSTANCE;
    private Cicerone cicerone;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        cicerone = new Cicerone();
    }

    public NavigatorHolder getNavigatorHolder() {
        return cicerone;
    }

    public Router getRouter() {
        return cicerone;
    }
}
```

## Команды навигатора
Для большинства задач предоставленных в библиотеке команд должно хватить, но их всегда можно дополнить собственными!  
+ Forward - переход на новый экран  
![](https://habrastorage.org/files/862/77e/b20/86277eb20b574dae8307ac4f64b0f090.png)
+ Back - возврат на предыдущий экран  
![](https://habrastorage.org/files/059/b63/2d3/059b632d3a7c4515a534b9e5e881c8f0.png)
+ BackTo - возврат к конкретному экрану в цепочке, либо на корневой, если указанный экран не найден  
![](https://habrastorage.org/files/a45/4f4/c34/a454f4c340764632ad0669014ad5550d.png)
+ Replace - замена текущего экрана на новый  
![](https://habrastorage.org/files/4ae/95c/fee/4ae95cfee4c04f038ad17d358ab08d07.png)
+ SystemMessage - показ системного сообщения (Alert, Toast, Snack и тд)
![](https://habrastorage.org/files/6e7/1a6/4ed/6e71a64edec04079bf33faa7ab39606f.png)

## Готовые навигаторы
Пакет _android_ предоставляет готовые навигаторы для _Activity_.  
Для их использования достаточно указать контейнер и передать _FragmentManager_.
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
    
    @Override
    public void applyCommand(Command command) {
        super.applyCommand(command);
        updateScreenNames(command);
    }
};
```
## Sample
Работу библиотеки, готовых навигаторов и другое можно посмотреть в _sample_ приложении.

![](https://habrastorage.org/files/16d/2ee/6e3/16d2ee6e33a0428eb4f0dcab8ce6b294.gif)

## Dependencies
...

## Лицензия
=======

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.