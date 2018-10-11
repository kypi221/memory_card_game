package com.kypi.demoproject.mvp.presenter;

import com.kypi.demoproject.base.BasePresenter;
import com.kypi.demoproject.domain.usecase.GameConfigUseCase;
import com.kypi.demoproject.domain.usecase.IReadDemoUseCase;
import com.kypi.demoproject.mvp.contracts.IReadDemoContract;
import com.kypi.demoproject.mvp.contracts.MainMenuContract;

import javax.inject.Inject;

public class MainMenuPresenter extends BasePresenter<MainMenuContract.View> implements MainMenuContract.Presenter {

    private final GameConfigUseCase gameConfigUseCase;

    @Inject
    public MainMenuPresenter(GameConfigUseCase gameConfigUseCase){
        this.gameConfigUseCase = gameConfigUseCase;
    }

    @Override
    public void loadCurrentConfigTime() {
        getMvpView().showCurrentConfigTime(gameConfigUseCase.getSettingTime());
    }

    @Override
    public void loadCurrentConfigLevel() {
        getMvpView().showCurrentConfigLevel(gameConfigUseCase.getSettingLevel());
    }

    @Override
    public void setConfigTime(long time) {
        gameConfigUseCase.setSettingTime(time);
        loadCurrentConfigTime();
    }

    @Override
    public void setConfigLevel(int level) {
        gameConfigUseCase.setSettingLevel(level);
        loadCurrentConfigLevel();
    }
}
