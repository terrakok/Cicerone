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
![](https://habrastorage.org/files/6e9/8b3/c61/6e98b3c61c104e35962c24fa0d470343.png)
+ Back - возврат на предыдущий экран  
![](https://habrastorage.org/files/2dc/5f1/d67/2dc5f1d672474ae78b6f5425f0488307.png)
+ BackTo - возврат к конкретному экрану в цепочке, либо на корневой, если указанный экран не найден  
![](https://habrastorage.org/files/562/40d/4d6/56240d4d6e4844c9add9a973696519f6.png)
+ Replace - замена текущего экрана на новый  
![](https://habrastorage.org/files/2fe/f5c/b3e/2fef5cb3e24e4504a3bfd9352a9b0377.png)
+ SystemMessage - показ системного сообщения (Allert, Toast, Snack и тд)  
![](https://habrastorage.org/files/94f/31b/fa9/94f31bfa903c4551a4c6652d71f126e4.png)

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
![](https://habrastorage.org/files/a4e/1b6/9df/a4e1b69df646494987ea1e85858eba60.png)
