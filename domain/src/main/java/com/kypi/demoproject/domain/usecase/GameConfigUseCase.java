package com.kypi.demoproject.domain.usecase;

import com.kypi.demoproject.domain.repository.GameConfigRepository;
import com.kypi.demoproject.domain.scheduler.SchedulerProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GameConfigUseCase extends BaseUseCase {

    private final GameConfigRepository gameConfigRepository;

    @Inject
    public GameConfigUseCase(SchedulerProvider schedulerProvider, GameConfigRepository gameConfigRepository) {
        super(schedulerProvider);
        this.gameConfigRepository = gameConfigRepository;
    }


    public void setSettingTime(long time) {
        gameConfigRepository.setSettingTime(time);
    }

    public long getSettingTime() {
        return gameConfigRepository.getSettingTime();
    }

    public void setSettingLevel(int level) {
        gameConfigRepository.setSettingLevel(level);
    }

    public int getSettingLevel() {
        return gameConfigRepository.getSettingLevel();
    }


}
