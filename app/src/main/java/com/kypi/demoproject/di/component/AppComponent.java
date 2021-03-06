package com.kypi.demoproject.di.component;

import com.kypi.demoproject.app.sound.SoundHelper;
import com.kypi.demoproject.di.module.AppModule;
import com.kypi.demoproject.di.module.ConfigModule;
import com.kypi.demoproject.di.module.RemoteModule;
import com.kypi.demoproject.di.module.RepositoryModule;
import com.kypi.demoproject.di.module.SchedulerModule;
import com.kypi.demoproject.domain.scheduler.SchedulerProvider;
import com.kypi.demoproject.domain.usecase.GameConfigUseCase;
import com.kypi.demoproject.domain.usecase.GamePlayUseCase;
import com.kypi.demoproject.domain.usecase.IReadDemoUseCase;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                AppModule.class,
                RepositoryModule.class,
                SchedulerModule.class,
                ConfigModule.class,
                RemoteModule.class,
        })
public interface AppComponent {
    SchedulerProvider schedulerProvider();
    SoundHelper soundHelper();

    IReadDemoUseCase demoUseCase();
    GameConfigUseCase gameConfigUseCase();
    GamePlayUseCase gamePlayUseCase();
}
