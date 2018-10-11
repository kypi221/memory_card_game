package com.kypi.demoproject.di.module;

import com.kypi.demoproject.data.repository.GameConfigRepositoryImpl;
import com.kypi.demoproject.data.repository.IReadRankingBookRepositoryImpl;
import com.kypi.demoproject.domain.repository.GameConfigRepository;
import com.kypi.demoproject.domain.repository.IReadDemoRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    public IReadDemoRepository provideDemoRepository(IReadRankingBookRepositoryImpl demoRepository) {
        return demoRepository;
    }

    @Provides
    @Singleton
    public GameConfigRepository provideGameConfigRepository(GameConfigRepositoryImpl gameConfigRepository) {
        return gameConfigRepository;
    }

}
