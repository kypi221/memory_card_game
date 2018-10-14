package com.kypi.demoproject.di.component;

import com.kypi.demoproject.mvp.activities.IReadDemoActivity;
import com.kypi.demoproject.mvp.activities.MainActivity;
import com.kypi.demoproject.di.module.ActivityModule;
import com.kypi.demoproject.di.module.PresenterModule;
import com.kypi.demoproject.di.scope.ActivityScope;
import com.kypi.demoproject.mvp.activities.MainMenuActivity;
import com.kypi.demoproject.mvp.activities.SelectLevelActivity;
import com.kypi.demoproject.mvp.activities.SelectTimeActivity;
import com.kypi.demoproject.mvp.activities.SinglePlayerGameActivity;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = {ActivityModule.class, PresenterModule.class})
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(IReadDemoActivity demoActivity);

    void inject(MainMenuActivity mainMenuActivity);

    void inject(SinglePlayerGameActivity singlePlayerGameActivity);

    void inject(SelectLevelActivity selectLevelActivity);

    void inject(SelectTimeActivity selectTimeActivity);
}
