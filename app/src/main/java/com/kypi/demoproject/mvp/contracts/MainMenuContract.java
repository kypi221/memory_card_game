package com.kypi.demoproject.mvp.contracts;

import com.kypi.demoproject.domain.entities.DemoObject;
import com.kypi.demoproject.domain.entities.IReadBookInfo;

import java.util.List;

public interface MainMenuContract {

    public interface View extends BaseContract.View {
        void showCurrentConfigTime(long time);
        void showCurrentConfigLevel(int level);
    }

    public interface Presenter extends BaseContract.BasePresenter<MainMenuContract.View> {
       void loadCurrentConfigTime();
       void loadCurrentConfigLevel();

       void setConfigTime(long time);
       void setConfigLevel(int level);
    }
}
